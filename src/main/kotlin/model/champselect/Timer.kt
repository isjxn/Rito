package dev.nanologic.model.champselect

import kotlinx.serialization.Serializable

@Serializable
data class Timer(
    val adjustedTimeLeftInPhase: Long,
    val internalNowInEpochMs: Long,
    val isInfinite: Boolean,
    val phase: String,
    val totalTimeInPhase: Long
)