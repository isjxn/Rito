package dev.nanologic.model.simpleinventoryjwt

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class SimpleInventoryJwtItems(
    val EVENT_PASS: List<@Contextual Any>,
    val BOOST: List<@Contextual Any>,
    val CHAMPION: List<Int>,
    val CHAMPION_SKIN: List<@Contextual Any>,
    val TFT_MAP_SKIN: List<Int>,
    val COMPANION: List<Int>
)