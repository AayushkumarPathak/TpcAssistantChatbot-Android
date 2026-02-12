package com.techmagnet.tpcassistantchatbot.data.remote

import com.techmagnet.tpcassistantchatbot.data.model.ChatRequest
import com.techmagnet.tpcassistantchatbot.data.model.ChatResponse
import com.techmagnet.tpcassistantchatbot.data.model.LoginRequest
import com.techmagnet.tpcassistantchatbot.data.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    @POST("chat")
    suspend fun sendMessage(
        @Header("Authorization") token: String,
        @Body request: ChatRequest
    ): ChatResponse
}
