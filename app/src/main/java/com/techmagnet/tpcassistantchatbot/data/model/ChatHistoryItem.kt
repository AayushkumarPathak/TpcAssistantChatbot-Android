package com.techmagnet.tpcassistantchatbot.data.model

data class ChatHistoryItem(
    val role: String,
    val parts: List<Part>
)

data class Part(
    val text: String
)
