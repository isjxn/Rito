package dev.nanologic.model.practicegame

import kotlinx.serialization.Serializable

@Serializable
data class PlayerGcoTokens(
    val __class: String,
    val idToken: String,
    val userInfoJwt: String,
    val summonerToken: String
)