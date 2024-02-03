package dev.nanologic.model.simpleinventoryjwt

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class SimpleInventoryJwtItems(
    val EVENT_PASS: List<String>,
    val BOOST: List<String>,
    val CHAMPION: List<Int>,
    val CHAMPION_SKIN: List<String>,
    val TFT_MAP_SKIN: List<Int>,
    val COMPANION: List<Int>
)