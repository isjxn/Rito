package dev.nanologic.model

import kotlinx.serialization.Serializable

@Serializable
data class Lol(
    val championId: String,
    val companionId: String,
    val damageSkinId: String,
    val gameMode: String,
    val gameQueueType: String,
    val gameStatus: String,
    val iconOverride: String,
    val isObservable: String,
    val level: String,
    val mapId: String,
    val mapSkinId: String,
    val masteryScore: String,
    val puuid: String,
    val queueId: String,
    val rankedPrevSeasonDivision: String,
    val rankedPrevSeasonTier: String,
    val regalia: String, // or JsonObject if you want to keep it as a raw JSON object and parse it separately
    val skinVariant: String,
    val skinname: String
)