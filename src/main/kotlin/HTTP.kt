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
import java.security.cert.X509Certificate
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