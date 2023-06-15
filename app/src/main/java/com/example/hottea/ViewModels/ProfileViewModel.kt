package com.example.hottea.ViewModels

import androidx.lifecycle.ViewModel
import com.example.hottea.repositories.AuthRepository
import com.example.hottea.repositories.FirestoreRepository

class ProfileViewModel (private val firestoreRepository: FirestoreRepository = FirestoreRepository(), authRepository: AuthRepository = AuthRepository()) : ViewModel() {

}