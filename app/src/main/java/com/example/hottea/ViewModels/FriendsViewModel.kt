package com.example.hottea.ViewModels

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hottea.models.User
import com.example.hottea.repositories.AuthRepository
import com.example.hottea.repositories.FirestoreRepository
import kotlinx.coroutines.launch

class FriendsViewModel(private val firestoreRepository: FirestoreRepository = FirestoreRepository(), authRepository: AuthRepository = AuthRepository()) : ViewModel(){
    private val FRIENDSDATA = mutableStateListOf<User?>()
    val friends : com.example.hottea.models.User? get() = FRIENDSDATA.firstOrNull()
    val userId = authRepository.getUserId()
    private var isInitialized = false



    init {
        if (!isInitialized) {
            getListOfFriends()
            isInitialized = true
            Log.d("FUCK SAKES", FRIENDSDATA.toString())
        }
    }



    private fun getListOfFriends() = viewModelScope.launch {
        firestoreRepository.getUserFriends(userId) { data ->
            data?.let {
                FRIENDSDATA.clear()
                FRIENDSDATA.addAll(listOf(it))
            }
        }
    }

}