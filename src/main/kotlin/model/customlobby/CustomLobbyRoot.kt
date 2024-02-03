package dev.nanologic.model.customlobby

import kotlinx.serialization.Serializable

@Serializable
data class CustomLobbyRoot(
    val body: CustomLobbyBody,
    val typeName: String
)