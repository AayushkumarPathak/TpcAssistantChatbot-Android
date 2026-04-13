package com.techmagnet.tpcassistantchatbot.ui.chat


import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.techmagnet.tpcassistantchatbot.R
import com.techmagnet.tpcassistantchatbot.data.model.*
import com.techmagnet.tpcassistantchatbot.data.repository.ChatRepository
import com.techmagnet.tpcassistantchatbot.service.ChatService
import com.techmagnet.tpcassistantchatbot.utils.TokenManager
import kotlinx.coroutines.launch


class ChatActivity : AppCompatActivity() {

    private lateinit var chatService: ChatService
    private lateinit var tokenManager: TokenManager
    private lateinit var repository: ChatRepository


    private lateinit var rvChat: RecyclerView
    private lateinit var etMessage: EditText
    private lateinit var btnSend: FloatingActionButton
    private lateinit var tvHumanRequired: TextView

    private lateinit var adapter: ChatAdapter
    private val chatHistory = mutableListOf<ChatHistoryItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        initViews()
        setupRecycler()
        setupClickListeners()

        tokenManager = TokenManager(this)
        repository = ChatRepository()
        chatService = ChatService(repository)

    }

    private fun initViews() {
        rvChat = findViewById(R.id.rvChat)
        etMessage = findViewById(R.id.etMessage)
        btnSend = findViewById(R.id.btnSend)
        tvHumanRequired = findViewById(R.id.tvHumanRequired)
    }

    private fun setupRecycler() {
        adapter = ChatAdapter(chatHistory)
        rvChat.layoutManager = LinearLayoutManager(this)
        rvChat.adapter = adapter
    }

    private fun setupClickListeners() {
        btnSend.setOnClickListener {
            val message = etMessage.text.toString().trim()
            if (message.isNotEmpty()) {
                sendMessage(message)
                etMessage.text.clear()
            }
        }
    }

    private fun sendMessage(message: String) {

        val userMessage = ChatHistoryItem(
            role = "student",
            parts = listOf(Part(message))
        )

        adapter.addMessage(userMessage)
        rvChat.scrollToPosition(chatHistory.size - 1)

        lifecycleScope.launch {
            try {

                val token = tokenManager.getToken()
                    ?: throw Exception("Token not found")

                val (botMessage, requiresHuman) =
                    chatService.processMessage(token, chatHistory, message)

                adapter.addMessage(botMessage)
                rvChat.scrollToPosition(chatHistory.size - 1)

                tvHumanRequired.visibility =
                    if (requiresHuman) android.view.View.VISIBLE
                    else android.view.View.GONE

            } catch (e: Exception) {
                Toast.makeText(this@ChatActivity,
                    "Error sending message",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }



    private fun getToken(): String? {
        val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
        return prefs.getString("jwt", null)
    }
}
