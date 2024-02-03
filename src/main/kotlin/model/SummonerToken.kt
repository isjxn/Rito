package dev.nanologic.model

import kotlinx.serialization.Serializable

@Serializable
data class SummonerToken(
    val expiry: Long,
    val scopes: List<String>,
    val token: String
)