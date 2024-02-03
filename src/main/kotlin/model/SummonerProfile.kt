package dev.nanologic.model

import kotlinx.serialization.Serializable

@Serializable
data class SummonerProfile(
    val accountId: Long,
    val displayName: String,
    val gameName: String,
    val internalName: String,
    val nameChangeFlag: Boolean,
    val percentCompleteForNextLevel: Int,
    val privacy: String,
    val profileIconId: Int,
    val puuid: String,
    val rerollPoints: RerollPoints,
    val summonerId: Long,
    val summonerLevel: Int,
    val tagLine: String,
    val unnamed: Boolean,
    val xpSinceLastLevel: Int,
    val xpUntilNextLevel: Int
)
