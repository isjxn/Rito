package dev.nanologic

import kotlinx.serialization.Serializable

@Serializable
data class SummonerSession(
    val accountId: Long,
    val connected: Boolean,
    val error: String?, // Assuming error can be null, represent it with a nullable type
    val idToken: String,
    val isInLoginQueue: Boolean,
    val isNewPlayer: Boolean,
    val puuid: String,
    val state: String,
    val summonerId: Long,
    val userAuthToken: String, // Assuming it can be an empty string
    val username: String
)