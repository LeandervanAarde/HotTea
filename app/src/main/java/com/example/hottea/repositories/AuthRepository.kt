package com.example.hottea.repositories

import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher

class AuthRepository {
    val currentUser: FirebaseUser? = Firebase.auth.currentUser
    fun hasUser(): Boolean = Firebase.auth.currentUser != null
    fun getUserId(): String = Firebase.auth.currentUser?.uid.orEmpty()

    suspend fun registerUser(email: String, password: String, createdUser: (String) -> Unit) = withContext(Dispatchers.IO){
        Firebase.auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{
            if(it.isComplete){
                Log.d("User is now registered, id:", it.result.user?.uid.toString())
                it.result.user?.uid?.let {it1 -> createdUser.invoke(it1)}
            } else {
                Log.d("Error registering new user", it.exception?.localizedMessage.toString())
                createdUser.invoke("")
            }
        }.await()
    }

    suspend fun loginUser (email: String, password: String, isCompleted: (Boolean) -> Unit) = withContext(Dispatchers.IO){
        Firebase.auth.signInWithEmailAndPassword(email, password).addOnCompleteListener{
            if(it.isComplete){
                Log.d("Logged in", true.toString())
            } else{
                Log.d("Login error", it.exception?.localizedMessage.toString())
                isCompleted.invoke(false)
            }
        }.await()

        fun signOutUser(){
            Firebase.auth.signOut()
        }

    }
}