package dev.nanologic.model

import kotlinx.serialization.Serializable

@Serializable
data class QuitGameMessage(
    val body: QuitGameMessageBody,
    val typeName: String
)