package com.techmagnet.tpcassistantchatbot.service

import com.techmagnet.tpcassistantchatbot.data.repository.ChatRepository
import com.techmagnet.tpcassistantchatbot.data.model.ChatHistoryItem
import com.techmagnet.tpcassistantchatbot.data.model.ChatRequest
import com.techmagnet.tpcassistantchatbot.data.model.Part

class ChatService(
    private val repository: ChatRepository
) {

    suspend fun processMessage(
        token: String,
        history: List<ChatHistoryItem>,
        message: String
    ): Pair<ChatHistoryItem, Boolean> {

        val response = repository.sendMessage(
            token,
            ChatRequest(history, message)
        )

        val botMessage = ChatHistoryItem(
            role = "assistant",
            parts = listOf(Part(response.message))
        )

        return Pair(botMessage, response.requiresHuman)
    }
}