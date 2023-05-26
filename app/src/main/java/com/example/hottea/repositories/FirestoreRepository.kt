package com.example.hottea.repositories

import android.util.Log
import com.example.hottea.models.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

const val USER_REF = "users"
class FirestoreRepository {
    val db = Firebase.firestore
    val userRef = db.collection((USER_REF))

    fun createUserInDatabase(
        uid: String,
        name: String,
        username: String,
        email: String,
        onSuccess: (Boolean) -> Unit,
        profileImage: String = ""
    ){
        userRef.document(uid)
            .set(User(uid, name, username, email, profileImage))
            .addOnSuccessListener {
                Log.d("LOGGER LogIn Success", "TRUE")
                onSuccess.invoke(true)
            }
            .addOnFailureListener {
                Log.d("LOGGER User not created", it.localizedMessage.toString())
                onSuccess.invoke(true)
            }
    }
}