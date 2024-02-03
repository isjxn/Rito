package dev.nanologic.model

import kotlinx.serialization.Serializable

@Serializable
data class Me(
    val availability: String,
    val gameName: String,
    val gameTag: String,
    val icon: Int,
    val id: String,
    val lastSeenOnlineTimestamp: Long?, // Assuming it can be null
    val lol: Lol,
    val name: String,
    val obfuscatedSummonerId: Long,
    val patchline: String,
    val pid: String,
    val platformId: String,
    val product: String,
    val productName: String,
    val puuid: String,
    val statusMessage: String,
    val summary: String,
    val summonerId: Long,
    val time: Long
)