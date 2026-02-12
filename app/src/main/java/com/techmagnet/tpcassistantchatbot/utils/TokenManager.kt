package com.techmagnet.tpcassistantchatbot.utils

import android.content.Context

class TokenManager(context: Context) {

    private val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        prefs.edit().putString("jwt", token).apply()
    }

    fun getToken(): String? {
        return prefs.getString("jwt", null)
    }

    fun clearToken() {
        prefs.edit().clear().apply()
    }
}