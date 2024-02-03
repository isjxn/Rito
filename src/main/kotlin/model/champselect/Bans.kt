package dev.nanologic.model.champselect

import kotlinx.serialization.Serializable

@Serializable
data class Bans(
    val myTeamBans: List<Int>,
    val numBans: Int,
    val theirTeamBans: List<Int>
)