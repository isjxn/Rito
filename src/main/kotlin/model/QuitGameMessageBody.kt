package dev.nanologic.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class QuitGameMessageBody(
    val body: String?,
    val clientId: String,
    val correlationId: String,
    val destination: String?,
    val headers: JsonObject,
    val messageId: String,
    val timeToLive: Double,
    val timestamp: Double
)