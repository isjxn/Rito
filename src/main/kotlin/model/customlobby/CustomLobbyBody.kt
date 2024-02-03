package dev.nanologic.model.customlobby

import dev.nanologic.model.champselect.MucJwtDto
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable


@Serializable
data class CustomLobbyBody(
    val banOrder: String?, // Using Any? as the type since null could represent various types
    val bannedChampions: List<String>, // Assuming empty list contains elements of a specific type you can replace Any with that type
    val creationTime: Long,
    val expiryTime: Double,
    val featuredGameInfo: FeaturedGameInfo,
    val gameMode: String,
    val gameMutators: List<String>,
    val gameQueueConfigId: Double,
    val gameState: String,
    val gameStateString: String,
    val gameType: String,
    val gameTypeConfigId: Double,
    val id: Double,
    val joinTimerDuration: Double,
    val mapId: Double,
    val maxNumPlayers: Double,
    val mucJwtDto: MucJwtDto,
    val name: String,
    val observers: List<String>, // Assuming empty list contains elements of a specific type you can replace Any with that type
    val optimisticLock: Double,
    val ownerSummary: OwnerSummary,
    val passbackDataPacket: String?, // Assuming this could be more specific, replace Any? accordingly
    val passbackUrl: String?, // Assuming this could be more specific, replace Any? accordingly
    val passwordSet: Boolean,
    val pickTurn: Double,
    val playerChampionSelections: List<PlayerChampionSelection>,
    val practiceGameRewardsDisabledReasons: List<String>, // Assuming empty list contains elements of a specific type you can replace Any with that type
    val queuePosition: Double,
    val queueTypeName: String,
    val roomName: String?, // Assuming this could be more specific, replace Any? accordingly
    val roomPassword: String,
    val spectatorDelay: Double,
    val spectatorsAllowed: String,
    val statusOfParticipants: String?, // Assuming this could be more specific, replace Any? accordingly
    val teamOne: List<TeamOne>,
    val teamOnePickOutcome: String?, // Assuming this could be more specific, replace Any? accordingly
    val teamTwo: List<String>, // Assuming empty list contains elements of a specific type you can replace Any with that type
    val teamTwoPickOutcome: String?, // Assuming this could be more specific, replace Any? accordingly
    val terminatedCondition: String,
    val terminatedConditionString: String
)
