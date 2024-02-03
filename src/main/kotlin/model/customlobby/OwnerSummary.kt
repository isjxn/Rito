package dev.nanologic.model.customlobby

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class OwnerSummary(
    val accountId: Double,
    val adjustmentFlags: Double,
    val clientInSynch: Boolean,
    val index: Double,
    val lastSelectedSkinIndex: Double,
    val locale: String?, // Using Any? as the type since null could represent various types
    val minor: Boolean,
    val originalAccountNumber: Double,
    val originalPlatformId: String,
    val partnerId: String,
    val pickMode: Double,
    val pickTurn: Double,
    val profileIconId: Double,
    val puuid: String,
    val queueRating: Double,
    val rankedTeamGuest: Boolean,
    val selectedPosition: String?, // Using Any? as the type since null could represent various types
    val selectedRole: String?, // Using Any? as the type since null could represent various types
    val summonerId: Double,
    val summonerInternalName: String,
    val summonerName: String,
    val teamOwner: Boolean,
    val teamParticipantId: String?, // Using Any? as the type since null could represent various types
    val teamRating: Double,
    val timeAddedToQueue: String?, // Using Any? as the type since null could represent various types
    val timeChampionSelectStart: Double,
    val timeGameCreated: Double,
    val timeMatchmakingStart: Double,
    val voterRating: Double
)