package dev.nanologic

import dev.nanologic.client.LeagueClient
import dev.nanologic.model.*
import dev.nanologic.model.champselect.ChampSelect
import dev.nanologic.model.champselect.MySelection
import dev.nanologic.model.practicegame.CreatePracticeGameRequestDto
import dev.nanologic.model.practicegame.GameMap
import dev.nanologic.model.practicegame.PlayerGcoTokens
import dev.nanologic.model.practicegame.PracticeGameConfig
import dev.nanologic.model.simpleinventoryjwt.SimpleInventoryJwtRoot
import io.ktor.client.call.*
import io.ktor.client.statement.*
import io.ktor.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.time.Instant
import java.util.*

class RiotApi(
    private val leagueClient: LeagueClient,
    private val http: HTTP = HTTP(leagueClient.clientAuthInfo)
) {
    suspend fun getCurrentSummoner(): SummonerProfile {
        val summonerProfile: SummonerProfile = http.getRequest("/lol-summoner/v1/current-summoner").body()
        return summonerProfile
    }
    private suspend fun quitCustomLobby() {
        val quitGameMessage: QuitGameMessage = http.postRequest("/lol-login/v1/session/invoke", StringValues.build {
            this.append("destination", "gameService")
            this.append("method", "quitGame")
            this.append("args", "")
        }).body()
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
        val clientVersion = getClientVersion()
        val simpleInventoryJwt = getSimpleInventoryJwt()
        val idToken = getIdToken()
        val userInfoJwt = getUserInfoJwt()
        val summonerToken = getSummonerToken()

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
            region = ""
        )

        val playerGcoTokens = PlayerGcoTokens(
            __class = "com.riotgames.platform.util.tokens.PlayerGcoTokens",
            idToken = idToken.token,
            userInfoJwt = userInfoJwt.token,
            summonerToken = summonerToken
        )

        val createPracticeGameRequestDto = CreatePracticeGameRequestDto(
            __class = "com.riotgames.platform.game.lcds.dto.CreatePracticeGameRequestDto",
            practiceGameConfig = practiceGameConfig,
            simpleInventoryJwt = simpleInventoryJwt.data.itemsJwt,
            playerGcoTokens = playerGcoTokens
        )
        val json = Json { prettyPrint = false }

        val encodedJson = withContext(Dispatchers.IO) {
            URLEncoder.encode(json.encodeToString(listOf(createPracticeGameRequestDto)), StandardCharsets.UTF_8)
        }

        val response = http.postRequest("/lol-login/v1/session/invoke", StringValues.build {
            this.append("destination", "gameService")
            this.append("method", "createPracticeGameV4")
            this.append("args", encodedJson)
        })


        println(response.bodyAsText())


    }

    private suspend fun getUserInfoJwt(): UserInfoJwt {
        val userInfoJwt: UserInfoJwt = http.getRequest("/lol-rso-auth/v1/authorization/access-token").body()
        return userInfoJwt
    }

    private suspend fun getIdToken(): IdToken {
        val idToken: IdToken = http.getRequest("/lol-rso-auth/v1/authorization/id-token").body()
        return idToken
    }

    private suspend fun getClientVersion(): String {
        val response = http.getRequest("/lol-patch/v1/game-version")
        return response.bodyAsText().replace("\"", "")
    }

    private suspend fun getSimpleInventoryJwt(): SimpleInventoryJwtRoot {
        val summoner = getSummoner()
        val summonerToken = getSummonerToken()
        val region = getRegion()

        val accountId = summoner.accountId
        val puuid = summoner.puuid
        val apiVersion = 1

        val simpleInventoryJwtRoot: SimpleInventoryJwtRoot = http.bearerGetRequest(
            "https://${region.friendlyName}-red.lol.sgp.pvp.net/lolinventoryservice-ledge/v${apiVersion}/inventories/simple?puuid=${puuid}&location=lolriot.ams1.${region.platformId}&inventoryTypes=CHAMPION&inventoryTypes=CHAMPION_SKIN&inventoryTypes=COMPANION&inventoryTypes=TFT_MAP_SKIN&inventoryTypes=EVENT_PASS&inventoryTypes=BOOST&accountId=${accountId}",
            summonerToken
        ).body()

        return simpleInventoryJwtRoot
    }

    private suspend fun getSummoner(): SummonerSession {
        val summonerSession: SummonerSession = http.getRequest("/lol-login/v1/session").body()
        return summonerSession
    }

    private suspend fun getSummonerToken(): String {
        var response = http.getRequest("/lol-league-session/v1/league-session-token").bodyAsText()
        response = response.replace("\"", "")
        return response
    }

    private suspend fun getRegion(): Regions {
        val region: Region = http.getRequest("/riotclient/region-locale").body()
        val me: Me = http.getRequest("/lol-chat/v1/me").body()
        val regions = Regions(region.webRegion, me.platformId.lowercase(Locale.getDefault()))
        return regions
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
            /*val customLobby =*/ //generateCustomLobby()
        //}

        //getClientVersion()
        //getSimpleInventoryJwt()
        //getIdToken()
        //getUserInfoJwt()
        generateCustomLobby()
    }
}