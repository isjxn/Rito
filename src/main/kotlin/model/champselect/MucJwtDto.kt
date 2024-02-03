package dev.nanologic.model.champselect

import kotlinx.serialization.Serializable

@Serializable
data class MucJwtDto(
    val channelClaim: String,
    val domain: String,
    val jwt: String,
    val targetRegion: String
)
