package com.example.hottea.ViewModels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hottea.models.AuthViewModel
import com.example.hottea.repositories.AuthRepository
import com.example.hottea.repositories.FirestoreRepository
import com.google.firebase.firestore.auth.User
import kotlinx.coroutines.launch

class UserViewModel(private val firestoreRepository: FirestoreRepository = FirestoreRepository(), authRepository: AuthRepository = AuthRepository()) : ViewModel(){
    private val PROFILEDATA = mutableStateOf<com.example.hottea.models.User?>(null)
    val profile : com.example.hottea.models.User? get() = PROFILEDATA.value
    val userId = authRepository.getUserId()
    private var isInitialized = false



    init {
        if (!isInitialized) {
            getUserInformation()
            isInitialized = true
            Log.d("FUCK SAKES", "AGAIN AND AGAIN")
        }
    }


    private fun getUserInformation() = viewModelScope.launch {
        firestoreRepository.getUserData(userId){ data ->
            data?.let {
                PROFILEDATA.value = it
            }

        }
    }

}

