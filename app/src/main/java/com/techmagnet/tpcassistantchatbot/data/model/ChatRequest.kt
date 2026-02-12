package com.techmagnet.tpcassistantchatbot.data.model

data class ChatRequest(
    val history: List<ChatHistoryItem>,
    val message: String
)
