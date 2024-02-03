package dev.nanologic

import dev.nanologic.client.LeagueClient
import dev.nanologic.model.QuitGameMessage
import dev.nanologic.model.SummonerProfile
import dev.nanologic.model.champselect.ChampSelect
import dev.nanologic.model.champselect.MySelection
import dev.nanologic.model.practicegame.CreatePracticeGameRequestDto
import dev.nanologic.model.practicegame.GameMap
import dev.nanologic.model.practicegame.PlayerGcoTokens
import dev.nanologic.model.practicegame.PracticeGameConfig
import io.ktor.client.call.*
import io.ktor.client.statement.*
import io.ktor.util.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
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

    private suspend fun generateCustomLobby() {
        // Assuming placeholder values for async calls
        val clientVersion = "1.0" // Placeholder for await getClientVersion()
        val simpleInventoryJwt = "simpleInventoryJwtPlaceholder" // Placeholder for await getSimpleInventoryJwt()
        val idToken = "idTokenPlaceholder" // Placeholder for await getIdToken()
        val userInfoJwt = "userInfoJwtPlaceholder" // Placeholder for await getUserInfoJwt()
        val summonerToken = "summonerTokenPlaceholder" // Placeholder for await getSummonerToken()

        val gameMap = GameMap(
            __class = "com.riotgames.platform.game.map.GameMap",
            description = "",
            displayName = "",
            mapId = 11,
            minCustomPlayers = 1,
            name = "",
            totalPlayers = 10
        )

        val practiceGameConfig = PracticeGameConfig(
            __class = "com.riotgames.platform.game.PracticeGameConfig",
            allowSpectators = "NONE",
            gameMap = gameMap,
            gameMode = "CLASSIC",
            gameMutators = emptyList(),
            gameName = "test",
            gamePassword = "",
            gameTypeConfig = 1,
            gameVersion = clientVersion,
            maxNumPlayers = 10,
            passbackDataPacket = null,
            passbackUrl = null,
            region = ""
        )

        val playerGcoTokens = PlayerGcoTokens(
            __class = "com.riotgames.platform.util.tokens.PlayerGcoTokens",
            idToken = idToken,
            userInfoJwt = userInfoJwt,
            summonerToken = summonerToken
        )

        val createPracticeGameRequestDto = CreatePracticeGameRequestDto(
            __class = "com.riotgames.platform.game.lcds.dto.CreatePracticeGameRequestDto",
            practiceGameConfig = practiceGameConfig,
            simpleInventoryJwt = simpleInventoryJwt,
            playerGcoTokens = playerGcoTokens
        )

        val response = http.postRequest("/lol-login/v1/session/invoke", StringValues.build {
            this.append("destination", "gameService")
            this.append("method", "createPracticeGameV4")
            this.append("args", listOf(Json.encodeToString(createPracticeGameRequestDto)).toString())
        })


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
        //quitCustomLobby()
        //val canCrash = getCheckTimerSelectedChamp()

        //if (canCrash) {
            /*val customLobby =*/ generateCustomLobby()
        //}
    }
}