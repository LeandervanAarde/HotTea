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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

class FriendsViewModel(
    private val firestoreRepository: FirestoreRepository = FirestoreRepository(),
    authRepository: AuthRepository = AuthRepository()
) : ViewModel() {
    private val FRIENDSDATA = mutableStateListOf<User?>()
    val friends: List<User?> get() = FRIENDSDATA.toList()
    val userId = authRepository.getUserId()
    private var isInitialized = false

    init {
        if (!isInitialized) {
            getListOfFriends()
            isInitialized = true
        }
    }

    private fun getListOfFriends() = viewModelScope.launch {
        try {
            val userFriends = firestoreRepository.getUserFriends(userId)
            FRIENDSDATA.clear()
            FRIENDSDATA.addAll(userFriends)

        } catch (e: Exception) {

        }
    }
}