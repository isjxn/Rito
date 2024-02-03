package dev.nanologic

import dev.nanologic.client.LeagueClient
import dev.nanologic.model.QuitGameMessage
import dev.nanologic.model.SummonerProfile
import io.ktor.client.call.*
import io.ktor.client.statement.*
import io.ktor.util.*

class RiotApi(
    private val leagueClient: LeagueClient,
    private val http: HTTP = HTTP(leagueClient.clientAuthInfo)
) {
    suspend fun getCurrentSummoner() {
        val summonerProfile: SummonerProfile = http.getRequest("/lol-summoner/v1/current-summoner").body()
        println(summonerProfile)
    }
    private suspend fun quitCustomLobby() {
        val quitGameMessage: QuitGameMessage = http.postRequest("/lol-login/v1/session/invoke", StringValues.build {
            this.append("destination", "gameService")
            this.append("method", "quitGame")
            this.append("args", "")
        }).body()

        println(quitGameMessage)
    }

    private suspend fun getCheckTimerSelectedChamp() {
        val session = http.getRequest("/lol-champ-select/v1/session")
        println(session.bodyAsText())
    }

    private fun generateCustomLobby() {

    }

    private fun startChampionSelection() {

    }

    private fun setClientReceivedGameMessage() {

    }

    private fun selectSpells() {

    }

    private fun selectChampionV2() {

    }

    private fun championSelectCompleted() {

    }

    private fun setClientReceivedMaestroMessage() {

    }

    suspend fun crashLobby() {
        quitCustomLobby().run {
            getCheckTimerSelectedChamp()
        }

    }
}