package dev.nanologic.model.practicegame

import kotlinx.serialization.Serializable

@Serializable
data class CreatePracticeGameRequestDto(
    val __class: String,
    val practiceGameConfig: PracticeGameConfig,
    val simpleInventoryJwt: String,
    val playerGcoTokens: PlayerGcoTokens
)