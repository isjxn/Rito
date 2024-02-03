package dev.nanologic.model.champselect

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class ChampSelect(
    val actions: List<List<Action>>,
    val allowBattleBoost: Boolean,
    val allowDuplicatePicks: Boolean,
    val allowLockedEvents: Boolean,
    val allowRerolling: Boolean,
    val allowSkinSelection: Boolean,
    val bans: Bans,
    val benchChampions: List<String>, // Assuming empty list or specific type if known
    val benchEnabled: Boolean,
    val boostableSkinCount: Int,
    val chatDetails: ChatDetails,
    val counter: Int,
    val gameId: Long,
    val hasSimultaneousBans: Boolean,
    val hasSimultaneousPicks: Boolean,
    val isCustomGame: Boolean,
    val isSpectating: Boolean,
    val localPlayerCellId: Int,
    val lockedEventIndex: Int,
    val myTeam: List<TeamMember>,
    val pickOrderSwaps: List<JsonObject>, // Assuming empty list or specific type if known
    val recoveryCounter: Int,
    val rerollsRemaining: Int,
    val skipChampionSelect: Boolean,
    val theirTeam: List<TeamMember>, // Assuming empty list or specific type if known
    val timer: Timer,
    val trades: List<String> // Assuming empty list or specific type if known
)
