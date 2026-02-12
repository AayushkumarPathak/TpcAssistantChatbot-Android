package com.techmagnet.tpcassistantchatbot.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.techmagnet.tpcassistantchatbot.R
import com.techmagnet.tpcassistantchatbot.data.model.LoginRequest
import com.techmagnet.tpcassistantchatbot.data.remote.RetrofitClient
import com.techmagnet.tpcassistantchatbot.ui.chat.ChatActivity
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val regNoEt = findViewById<EditText>(R.id.etRegNo)
        val passEt = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val regNo = regNoEt.text.toString()
            val pass = passEt.text.toString()

            login(regNo, pass)
        }
    }

    private fun login(regNo: String, password: String) {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.login(
                    LoginRequest(regNo, password)
                )

                saveToken(response.token)

                startActivity(Intent(this@LoginActivity, ChatActivity::class.java))
                finish()

            } catch (e: Exception) {
                Toast.makeText(this@LoginActivity, "Login failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveToken(token: String) {
        val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
        prefs.edit().putString("jwt", token).apply()
    }
}
