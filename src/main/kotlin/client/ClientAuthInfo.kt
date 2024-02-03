package dev.nanologic.client

data class ClientAuthInfo(
    val riotClientAuthToken: String,
    val riotClientPort: String,
    val remotingAuthToken: String,
    val remotingPort: String,
)
