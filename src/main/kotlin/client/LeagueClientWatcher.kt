package dev.nanologic.client

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.concurrent.atomic.AtomicBoolean

class LeagueClientWatcher {
    fun observe(): LeagueClient? {
        var leagueClient: LeagueClient? = null
        val found = AtomicBoolean(false)
        while(!found.get()) {
            if (Thread.currentThread().isInterrupted) return null

            ProcessHandle.allProcesses().forEach {process ->
                val processName = process.info().command().orElse("")

                if (processName.equals("LeagueClientUx") || processName.endsWith("LeagueClientUx.exe") || processName.equals("A:\\Riot Games\\League of Legends\\LeagueClientUx.exe")) {
                    val clientAuthInfo = getClientAuthInfo(process.pid())
                    if (clientAuthInfo != null) {
                        leagueClient = LeagueClient(clientAuthInfo, process.pid(), process)
                        found.set(true)
                    }
                }
            }
        }

        return leagueClient
    }

    private fun getClientAuthInfo(pid: Long): ClientAuthInfo? {
        val command = listOf(
            "powershell.exe",
            "-Command",
            String.format("WMIC PROCESS WHERE ProcessId=%d get commandline", pid)
        )

        val processBuilder = ProcessBuilder(command)
        val process = processBuilder.start()
        val reader = BufferedReader(InputStreamReader(process.inputStream))
        val output = reader.readText()

        val riotClientAuthToken = getRiotClientAuthToken(output)
        val riotClientPort = getRiotClientPort(output)
        val remotingAuthToken = getRemotingAuthToken(output)
        val remotingPort = getRemotingPort(output)

        if (riotClientAuthToken != null
            && riotClientPort != null
            && remotingAuthToken != null
            && remotingPort != null
        ) {
            val clientAuthInfo = ClientAuthInfo(
                riotClientAuthToken,
                riotClientPort,
                remotingAuthToken,
                remotingPort
            )

            return clientAuthInfo
        }

        return null
    }

    private fun getRiotClientAuthToken(output: String): String? {
        val regex: Regex = "--riotclient-auth-token=(\\S+)".toRegex()
        val matcher = regex.find(output)
        if (matcher?.value?.isNotEmpty() == true) {
            var riotClientAuthToken: String = matcher.value
            riotClientAuthToken = riotClientAuthToken.substring(0, riotClientAuthToken.length - 1)
            riotClientAuthToken = riotClientAuthToken.replace("--riotclient-auth-token=", "")
            return riotClientAuthToken
        } else {
            println("authToken not found")
        }
        return null
    }

    fun getRiotClientPort(output: String): String? {
        val regex: Regex = "--riotclient-app-port=(\\S+)".toRegex()
        val matcher = regex.find(output)
        if (matcher?.value?.isNotEmpty() == true) {
            var riotClientPort: String = matcher.value
            riotClientPort = riotClientPort.substring(0, riotClientPort.length - 1)
            riotClientPort = riotClientPort.replace("--riotclient-app-port=", "")
            return riotClientPort
        } else {
            println("riotClientAppPort not found")
        }
        return null
    }

    fun getRemotingAuthToken(output: String): String? {
        val regex: Regex = "--remoting-auth-token=(\\S+)".toRegex()
        val matcher = regex.find(output)
        if (matcher?.value?.isNotEmpty() == true) {
            var remotingAuthToken: String = matcher.value
            remotingAuthToken = remotingAuthToken.substring(0, remotingAuthToken.length - 1)
            remotingAuthToken = remotingAuthToken.replace("--remoting-auth-token=", "")
            return remotingAuthToken
        } else {
            println("remotingAuthToken not found")
        }
        return null
    }

    fun getRemotingPort(output: String): String? {
        val regex: Regex = "--app-port=(\\S+)".toRegex()
        val matcher = regex.find(output)
        if (matcher?.value?.isNotEmpty() == true) {
            var remotingPort: String = matcher.value
            remotingPort = remotingPort.substring(0, remotingPort.length - 1)
            remotingPort = remotingPort.replace("--app-port=", "")
            return remotingPort
        } else {
            println("authToken not found")
        }
        return null
    }
}