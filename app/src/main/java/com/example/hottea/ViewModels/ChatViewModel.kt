package com.example.hottea.ViewModels

import android.app.DownloadManager
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hottea.models.Conversation
import com.example.hottea.models.Message
import com.example.hottea.models.User
import com.example.hottea.repositories.AuthRepository
import com.example.hottea.repositories.FirestoreRepository
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ChatViewModel (
    private val repository: FirestoreRepository = FirestoreRepository(),
    authRepository: AuthRepository = AuthRepository()
): ViewModel() {
    private val _messageList = mutableStateListOf<Message>()
    val messageList: List<Message> = _messageList

    var messageListener: ListenerRegistration? = null

    val userId = authRepository.getUserId()
    private val PROFILEDATA = mutableStateOf<User?>(null)
    val profile : com.example.hottea.models.User? get() = PROFILEDATA.value

    private var CONVERSATIONSDATA =  MutableStateFlow<Conversation?>(null)
    val conversations: MutableStateFlow<Conversation?> get() = CONVERSATIONSDATA

    init {
        getUserInformation()
    }

    private fun getUserInformation() = viewModelScope.launch {
        repository.getUserData(userId){ data ->
            data?.let {
                PROFILEDATA.value = it
            }

        }
    }


    fun sendNewMessage(body: String, chatId: String) = viewModelScope.launch {
        if(body.isNotBlank() && chatId.isNotBlank()){

            var sentMessage = Message(
                userId = userId,
                message = body
            )

            repository.addNewMessage(
                msg = sentMessage,
                chatId = chatId
            ){
                Log.d("AAA added message success", it.toString())
            }
        }
    }



    fun getRealtimeMessages(chatId: String) {
        Log.d("AA start messages", chatId)

        val collectionRef = Firebase.firestore
            .collection("conversations")
            .document(chatId)
            .collection("messages")
            .orderBy("time", Query.Direction.DESCENDING)
            .limit(50)
        messageListener = collectionRef.addSnapshotListener { snapshot, e ->
            Log.d("AAA Listening...", "YES!!")
            if(e != null){ // there was an error message
                Log.d("AAA listener went wrong", e.localizedMessage ?: "")
                return@addSnapshotListener
            }

            if (snapshot != null){
                // Converts the result data to our messages
                Log.d("AAA received realtime...", snapshot.toString())

                _messageList.clear() // first clear the data

                //then add the new data
                for(document in snapshot){
                    _messageList.add(document.toObject(Message::class.java))
                }
                Log.d("AAA received new messages: ", snapshot.toString())
            }
        }
    }

    override fun onCleared() {
        Log.d("AAA Stop view model...", "YES!")
        messageListener?.remove()
        messageListener = null
    }
}