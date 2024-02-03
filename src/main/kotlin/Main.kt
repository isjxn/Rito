package dev.nanologic

import dev.nanologic.client.LeagueClientWatcher
import kotlinx.coroutines.runBlocking

fun main() {
    val leagueClientWatcher = LeagueClientWatcher()
    val leagueClient = leagueClientWatcher.observe()
    leagueClient?.let {
        println("LeagueClient:")
        println("riotClientAuthToken: ${it.clientAuthInfo.riotClientAuthToken}")
        println("riotClientPort: ${it.clientAuthInfo.riotClientPort}")
        println("remotingAuthToken: ${it.clientAuthInfo.remotingAuthToken}")
        println("remotingPort: ${it.clientAuthInfo.remotingPort}")

        val riotApi = RiotApi(it)

        runBlocking {
            //riotApi.getCurrentSummoner()
            riotApi.crashLobby()
        }

    }
}