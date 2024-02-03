package dev.nanologic.model.customlobby

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class FeaturedGameInfo(
    val championVoteInfoList: List<String> // Assuming empty list contains elements of a specific type you can replace Any with that type
)