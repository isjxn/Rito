package dev.nanologic.model.practicegame

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class PracticeGameConfig(
    val __class: String,
    var allowSpectators: String,
    var gameMap: GameMap,
    var gameMode: String,
    var gameMutators: List<String>,
    var gameName: String,
    var gamePassword: String,
    var gameTypeConfig: Int,
    var gameVersion: String,
    var maxNumPlayers: Int,
    var region: String
)