package dev.nanologic.model.customlobby

import kotlinx.serialization.Serializable

@Serializable
data class PlayerChampionSelection(
    val championId: Double,
    val puuid: String,
    val selectedSkinIndex: Double,
    val spell1Id: Double,
    val spell2Id: Double,
    val summonerId: Double,
    val summonerInternalName: String
)