package dev.nanologic.model.simpleinventoryjwt

import kotlinx.serialization.Serializable

@Serializable
data class SimpleInventoryJwtRoot(
    val data: SimpleInventoryJwtData
)