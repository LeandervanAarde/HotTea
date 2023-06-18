package com.example.hottea.ViewModels

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hottea.models.AuthViewModel
import com.example.hottea.repositories.AuthRepository
import com.example.hottea.repositories.FirebaseStorageRepository
import com.example.hottea.repositories.FirestoreRepository
import com.google.firebase.firestore.auth.User
import kotlinx.coroutines.launch

class UserViewModel(private val firestoreRepository: FirestoreRepository = FirestoreRepository(), authRepository: AuthRepository = AuthRepository(), storageRepository: FirebaseStorageRepository = FirebaseStorageRepository()) : ViewModel(){
    private val PROFILEDATA = mutableStateOf<com.example.hottea.models.User?>(null)
    val profile : com.example.hottea.models.User? get() = PROFILEDATA.value
    val userId = authRepository.getUserId()
    private var isInitialized = false
    var profileState by mutableStateOf(com.example.hottea.models.User())
        private set

    var prevImage: String = ""

    fun handleProfileImageChange(value: Uri){
        profileState = profileState.copy(profileImage = value.toString())
        PROFILEDATA.value?.profileImage = profileState.profileImage
    }
    init {
        if (!isInitialized) {
            getUserInformation()
            isInitialized = true
            Log.d("FUCK SAKES", PROFILEDATA.value.toString())
        }
    }

    private fun getUserInformation() = viewModelScope.launch {
        firestoreRepository.getUserData(userId){ data ->
            data?.let {
                PROFILEDATA.value = it
            }

        }
    }

    fun removeUser(navToRegister: () -> Unit) = viewModelScope.launch {
        firestoreRepository.deleteAccount(userId){
            navToRegister()
        }
    }

}


