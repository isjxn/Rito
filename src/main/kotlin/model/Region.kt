package dev.nanologic.model

import kotlinx.serialization.Serializable

@Serializable
data class Region(
    val locale: String,
    val region: String,
    val webLanguage: String,
    val webRegion: String
)