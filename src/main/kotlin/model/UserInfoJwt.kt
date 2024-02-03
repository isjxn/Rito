package dev.nanologic.model

import kotlinx.serialization.Serializable

@Serializable
data class UserInfoJwt(
    val expiry: Long,
    val scopes: List<String>,
    val token: String
)