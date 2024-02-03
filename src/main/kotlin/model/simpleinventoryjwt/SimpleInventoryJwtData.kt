package dev.nanologic.model.simpleinventoryjwt

import kotlinx.serialization.Serializable

@Serializable
data class SimpleInventoryJwtData(
    val items: SimpleInventoryJwtItems,
    val itemsJwt: String,
    val expires: String,
    val containsf2P: Boolean
)