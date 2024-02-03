package dev.nanologic.model.champselect

import kotlinx.serialization.Serializable

@Serializable
data class ChatDetails(
    val mucJwtDto: MucJwtDto,
    val multiUserChatId: String,
    val multiUserChatPassword: String
)