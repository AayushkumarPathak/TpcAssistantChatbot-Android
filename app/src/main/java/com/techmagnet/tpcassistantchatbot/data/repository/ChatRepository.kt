package com.techmagnet.tpcassistantchatbot.data.repository

import com.techmagnet.tpcassistantchatbot.data.model.ChatRequest
import com.techmagnet.tpcassistantchatbot.data.model.ChatResponse
import com.techmagnet.tpcassistantchatbot.data.remote.RetrofitClient

class ChatRepository {

    suspend fun sendMessage(
        token: String,
        request: ChatRequest
    ): ChatResponse {
        return RetrofitClient.api.sendMessage("Bearer $token", request)
    }
}