package com.example.hottea.repositories

import android.util.Log
import com.example.hottea.models.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.util.Objects

const val USER_REF = "users"
class FirestoreRepository {
    val db = Firebase.firestore
    val userRef = db.collection((USER_REF))
    val userId = AuthRepository()

    fun createUserInDatabase(
        uid: String,
        name: String,
        username: String,
        email: String,
        onSuccess: (Boolean) -> Unit,
        profileImage: String = "",
        status: String = ""
    ) {
        userRef.document(uid)
            .set(User(uid, name, username, email, profileImage, status))
            .addOnSuccessListener {
                Log.d("LOGGER LogIn Success", "TRUE")
                onSuccess.invoke(true)
            }
            .addOnFailureListener {
                Log.d("LOGGER User not created", it.localizedMessage.toString())
                onSuccess.invoke(true)
            }
    }

//    suspend fun getUserData(uid: String, onSuccess: (User?) -> Unit) {
//        userRef.document(uid).get().addOnSuccessListener { document ->
//            Log.d("DATA", "Document Snapshot data: ${document.data}")
//            onSuccess.invoke(document.toObject(User::class.java))
//        }.addOnFailureListener { exception ->
//            Log.i("USER", "Get failed with", exception)
//            onSuccess.invoke(null)
//        }.await()
//    }
    suspend fun  getUserData(uid: String, onSuccess: (User?) -> Unit){
        userRef.document(uid).get().addOnSuccessListener { document ->
            if(document != null){
                onSuccess.invoke(document.toObject(User::class.java))
                Log.i("USER", "Get SUCCESS",)
            }
        }
        .addOnFailureListener { exception ->
            Log.i("USER", "Get failed with", exception)
            onSuccess.invoke(null)
        }.await()
    }
}