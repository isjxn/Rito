package dev.nanologic

import dev.nanologic.client.LeagueClient
import dev.nanologic.model.QuitGameMessage
import dev.nanologic.model.SummonerProfile
import dev.nanologic.model.champselect.ChampSelect
import dev.nanologic.model.champselect.MySelection
import io.ktor.client.call.*
import io.ktor.client.statement.*
import io.ktor.util.*
import java.time.Instant
import java.util.Date

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

    private suspend fun getCheckTimerSelectedChamp(): Boolean {
        val champSelect: ChampSelect = http.getRequest("/lol-champ-select/v1/session").body()
        val timer = champSelect.timer

        if (champSelect.isCustomGame) {
            println("You cannot use crash lobby exploit in a custom game!")
            return false
        }

        val mySelection: MySelection = http.getRequest("/lol-champ-select/v1/session/my-selection").body()

        if (mySelection.championId == 0) {
            println("You need to confirm your champion before crashing the lobby!")
            return false
        }

        if(timer.phase == "FINALIZATION") {
            val current = Instant.now().toEpochMilli()
            val remaining = (timer.internalNowInEpochMs + timer.adjustedTimeLeftInPhase) - current

            if(remaining < 11000) {
                println("Not enough time to crash lobby, you need a minimum of 11 seconds!")
                return false
            }
        }

        return true
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
        //quitCustomLobby().run {
            getCheckTimerSelectedChamp()
        //}

    }
}