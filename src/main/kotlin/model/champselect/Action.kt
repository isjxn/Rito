package dev.nanologic.model.champselect

import kotlinx.serialization.Serializable

@Serializable
data class Action(
    val actorCellId: Int,
    val championId: Int,
    val completed: Boolean,
    val id: Int,
    val isAllyAction: Boolean,
    val isInProgress: Boolean,
    val pickTurn: Int,
    val type: String
)