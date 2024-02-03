package dev.nanologic.model

import kotlinx.serialization.Serializable

@Serializable
data class RerollPoints(
    val currentPoints: Int,
    val maxRolls: Int,
    val numberOfRolls: Int,
    val pointsCostToRoll: Int,
    val pointsToReroll: Int
)