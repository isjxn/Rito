package dev.nanologic.model.champselect

import kotlinx.serialization.Serializable

@Serializable
data class MySelection(
    val assignedPosition: String,
    val cellId: Int,
    val championId: Int,
    val championPickIntent: Int,
    val nameVisibilityType: String,
    val obfuscatedPuuid: String,
    val obfuscatedSummonerId: Long,
    val puuid: String,
    val selectedSkinId: Int,
    val spell1Id: Int,
    val spell2Id: Int,
    val summonerId: Long,
    val team: Int,
    val wardSkinId: Int
)
