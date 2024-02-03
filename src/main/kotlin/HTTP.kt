package dev.nanologic

import dev.nanologic.client.ClientAuthInfo
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.util.*
import java.net.URI
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.security.cert.X509Certificate
import java.util.*
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class HTTP(
    private val clientAuthInfo: ClientAuthInfo,
    private val client: HttpClient = HttpClient(CIO) {
        this.install(ContentNegotiation) {
            this.json()
        }
        engine {
            https {
                trustManager = TrustAllX509TrustManager
            }
            // Timeout settings
            endpoint {
                requestTimeout = 100_000
                connectTimeout = 100_000
                socketTimeout = 100_000
            }
        }
    }
) {
    object TrustAllX509TrustManager : X509TrustManager {
        override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
        }

        override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
        }

        override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
    }

    suspend fun postRequest(path: String, parameters: StringValues, port: Int = clientAuthInfo.remotingPort.toInt()): HttpResponse {
        val response: HttpResponse = client.post {
            this.url {
                it.protocol = URLProtocol.HTTPS
                it.host = "127.0.0.1"
                it.port = port
                it.path(path)
                it.encodedParameters.appendAll(parameters)
            }
            this.basicAuth("riot", clientAuthInfo.remotingAuthToken)
            this.contentType(ContentType.Application.Json)
        }

        //println(response.responseTime)
        //println(response.request.url)
        return response
    }

    suspend fun newPostRequest(path: String, parameters: StringValues, port: Int = clientAuthInfo.remotingPort.toInt()): java.net.http.HttpResponse<String>? {
        // Create a trust manager that does not validate certificate chains
        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            override fun getAcceptedIssuers() = arrayOf<java.security.cert.X509Certificate>()
            override fun checkClientTrusted(certs: Array<java.security.cert.X509Certificate>, authType: String) {}
            override fun checkServerTrusted(certs: Array<java.security.cert.X509Certificate>, authType: String) {}
        })

        // Install the all-trusting trust manager
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, trustAllCerts, java.security.SecureRandom())
        // Create all-trusting host name verifier
        val allHostsValid = { _: String, _: javax.net.ssl.SSLSession -> true }

        val httpClient = java.net.http.HttpClient.newBuilder()
            .sslContext(sslContext)
            .sslParameters(sslContext.defaultSSLParameters)
            .build()

        val queryString = parameters.entries().joinToString("&") { entry ->
            entry.value.joinToString(",") { value ->
                "${URLEncoder.encode(entry.key, StandardCharsets.UTF_8)}=${URLEncoder.encode(value, StandardCharsets.UTF_8)}"
            }
        }

        val uri = URI.create("https://127.0.0.1:$port$path?$queryString")

        val request = java.net.http.HttpRequest.newBuilder()
            .uri(uri)
            .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("riot:${clientAuthInfo.remotingAuthToken}".toByteArray()))
            .header("Content-Type", "application/json")
            .POST(java.net.http.HttpRequest.BodyPublishers.noBody())
            .build()

        return httpClient.send(request, java.net.http.HttpResponse.BodyHandlers.ofString())
    }

    suspend fun getRequest(path: String, parameters: StringValues = StringValues.Empty): HttpResponse {
        val response: HttpResponse = client.get {
            this.url {
                it.protocol = URLProtocol.HTTPS
                it.host = "127.0.0.1"
                it.port = clientAuthInfo.remotingPort.toInt()
                it.path(path)
                it.encodedParameters.appendAll(parameters)
            }
            this.basicAuth("riot", clientAuthInfo.remotingAuthToken)
        }

        println(response.request.url)
        return response
    }

    suspend fun bearerGetRequest(url: String, token: String): HttpResponse {
        val response = client.get(url) {
            this.bearerAuth(token)
        }

        print(response.request.url)

        return response
    }
}