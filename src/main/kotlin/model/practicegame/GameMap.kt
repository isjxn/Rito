package dev.nanologic.model.practicegame

import kotlinx.serialization.Serializable

@Serializable
data class GameMap(
    val __class: String,
    var description: String,
    var displayName: String,
    var mapId: Int,
    var minCustomPlayers: Int,
    var name: String,
    var totalPlayers: Int
)