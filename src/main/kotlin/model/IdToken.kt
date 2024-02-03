package dev.nanologic.model

import kotlinx.serialization.Serializable

@Serializable
data class IdToken(
    val expiry: Long,
    val token: String
)