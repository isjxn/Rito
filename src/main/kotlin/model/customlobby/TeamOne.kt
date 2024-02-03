package dev.nanologic.model.customlobby

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class TeamOne(
    val accountId: Double,
    val adjustmentFlags: Double,
    val clientInSynch: Boolean,
    val index: Double,
    val lastSelectedSkinIndex: Double,
    val locale: String?, // Assuming this could be more specific, replace Any? accordingly
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
    val selectedPosition: String?, // Assuming this could be more specific, replace Any? accordingly
    val selectedRole: String?, // Assuming this could be more specific, replace Any? accordingly
    val summonerId: Double,
    val summonerInternalName: String,
    val summonerName: String,
    val teamOwner: Boolean,
    val teamParticipantId: String?, // Assuming this could be more specific, replace Any? accordingly
    val teamRating: Double,
    val timeAddedToQueue: String?, // Assuming this could be more specific, replace Any? accordingly
    val timeChampionSelectStart: Double,
    val timeGameCreated: Double,
    val timeMatchmakingStart: Double,
    val voterRating: Double
)