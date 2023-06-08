package com.example.hottea.repositories

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.example.hottea.models.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await
import java.io.File
import java.util.Objects
//import kotlin.coroutines.jvm.internal.CompletedContinuation.context

const val USER_REF = "users"
class FirestoreRepository {
    val db = Firebase.firestore
    val storage = Firebase.storage
    val userRef = db.collection((USER_REF))
    val userId = AuthRepository()
    var storageRef = storage.reference
    var profileImageRef = storageRef.child("/profileImages")

    fun createUserInDatabase(
        uid: String,
        name: String,
        username: String,
        email: String,
        onSuccess: (Boolean) -> Unit,
        profileImage: String = "",
        status: String = "No status...",
        friends: List<String> = emptyList(),
        chats: List<String> = emptyList()
    ) {
        userRef.document(uid)
            .set(User(uid, name, username, email, profileImage, status, friends, chats))
            .addOnSuccessListener {
                Log.d("LOGGER LogIn Success", "TRUE")
                onSuccess.invoke(true)
            }
            .addOnFailureListener {
                Log.d("LOGGER User not created", it.localizedMessage.toString())
                onSuccess.invoke(true)
            }
    }

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

     fun updateUserData(uid: String, username: String, status: String, newImage: String ){
         var file = Uri.fromFile(File(newImage))
         var newImageRef = storageRef.child("/profileImages/${file.lastPathSegment}")
         var uploadTask = newImageRef.putFile(file)

//         uploadTask
//             .addOnFailureListener { e ->
//                 Log.w("IMAGEUPT", "Error updating document", e)
//
//             }
//             .addOnSuccessListener {
//                 val task = uploadTask.continueWithTask { task ->
//                     if (!task.isSuccessful) {
//                         task.exception?.let {
//                             throw it
//                         }
//                     }
//                     newImageRef.downloadUrl
//                 }.addOnCompleteListener { task ->
//                     if (task.isSuccessful) {
//                         val downloadUri = task.result
//                         userRef.document(uid)
//                             .update("username", username, "status", status, "image" , downloadUri)
//                             .addOnSuccessListener { Log.d("UPDATE", "DocumentSnapshot successfully updated!") }
//                             .addOnFailureListener { e -> Log.w("UPDATE", "Error updating document", e) }
//                     } else {
//
//                     }
//                 }
//             }

         userRef.document(uid)
             .update("username", username, "status", status)
             .addOnSuccessListener { Log.d("UPDATE", "DocumentSnapshot successfully updated!") }
             .addOnFailureListener { e -> Log.w("UPDATE", "Error updating document", e) }


    }

    fun addFriend(email: String, uid: String){
        userRef
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { documents ->
                for(document in documents){
                    Log.d("FRIEND", document.toString())
                    userRef.document(uid).update("friends", document.data)
                        .addOnSuccessListener {
                            Log.d("FRIEND", document.toString())
                        }
                        .addOnFailureListener { e ->
                            Log.w("FRIEND", "Error updating document", e)
                        }
                }


            }
            .addOnFailureListener {
              e -> Log.w("FRIEND", "Error updating document", e)
            }
    }

}



//arguments = listOf(navArgument("chatId") { type = NavType.StringType; defaultValue = "chat1234" })