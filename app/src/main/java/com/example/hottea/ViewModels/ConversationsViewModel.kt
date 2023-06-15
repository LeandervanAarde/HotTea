package com.example.hottea.ViewModels

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hottea.models.Conversation
import com.example.hottea.repositories.AuthRepository
import com.example.hottea.repositories.FirestoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ConversationsViewModel (private val firestoreRepository: FirestoreRepository = FirestoreRepository(), authRepository: AuthRepository = AuthRepository()) : ViewModel(){
   private val CONVERSATIONSDATA = mutableStateListOf<Conversation>()
   val conversations: List<Conversation?> get() = CONVERSATIONSDATA.toList()
   val userId = authRepository.getUserId()
   private var isInitialized = false
    init {
        if (!isInitialized) {
            getListOfConversations()
            isInitialized = true
        }
    }
    private fun updateConversations(conversation: Conversation) {
        CONVERSATIONSDATA.add(conversation)
    }
    private fun getListOfConversations() = viewModelScope.launch {
        try {
            val userConversation = firestoreRepository.getAllConversations(userId)
            CONVERSATIONSDATA.clear()
            CONVERSATIONSDATA.addAll(userConversation)
            Log.i("CONVERSAT", conversations.toString())
        } catch (e: Exception) {
                Log.d("ERR", e.localizedMessage)
        }
    }

    fun getNewConversations(conversation: Conversation){
        CONVERSATIONSDATA.add(conversation)
    }
}