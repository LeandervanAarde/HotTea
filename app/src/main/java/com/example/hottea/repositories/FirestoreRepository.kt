package com.example.hottea.repositories

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.platform.LocalContext
import com.example.hottea.models.Conversation
import com.example.hottea.models.User
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.google.gson.Gson
import io.grpc.internal.JsonParser
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
        profileImage: String = "https://firebasestorage.googleapis.com/v0/b/hottea-44cd0.appspot.com/o/profileImage.jpg?alt=media&token=ea6a630a-3dd0-4762-9509-1c91c9fd16dc&_gl=1*iq0cin*_ga*MTg3MzQ4NjU1LjE2ODI4NDIwMTg.*_ga_CW55HF8NVT*MTY4NjM5Mzg4MC42My4xLjE2ODYzOTQwOTAuMC4wLjA.",
        status: String = "No status...",
        available: Boolean = true
    ) {
        userRef.document(uid)
            .set(User(uid, name, username, email, profileImage, status, available ))
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
        // Get the user document with the specified email address.
        val user = db.collection(USER_REF).document(uid)
        user
            .get()
            .addOnSuccessListener { document ->
                // Iterate through the documents and add each one to the user's friends collection.
                if (document != null) {
                    val friendsRef = user.collection("friends")
                    val friendDocument = mapOf("email" to email)
                    friendsRef.add(friendDocument)
                        .addOnSuccessListener {
                            Log.d("FRIEND", "Friend added successfully")
                        }
                        .addOnFailureListener { e ->
                            Log.w("FRIEND", "Error adding friend", e)
                        }
                } else {
                    Log.w("FRIEND", "User not found")
                }
            }
            .addOnFailureListener {
                    e -> Log.w("FRIEND", "Error getting user document", e)
            }
    }

    suspend fun getUserFriends(uid: String): MutableList<User> {
        val userList = mutableListOf<User>()
        try {
            val friendsQuerySnapshot = db.collection("users").document(uid).collection("friends").get().await()

            val emailList = mutableListOf<String>()
            for (document in friendsQuerySnapshot.documents) {
                val email = document.getString("email")
                email?.let { emailList.add(it) }
            }
            val userQuerySnapshot = userRef.whereIn("email", emailList).get().await()
            for (userDocument in userQuerySnapshot.documents) {
                val user = userDocument.toObject(User::class.java)
                user?.let { userList.add(it) }
            }
        } catch (e: Exception) {
          Log.d("ERROR", e.toString())
        }
        return userList
    }

    fun createNewConversation(userOne: String, userTwo: String, lastConversation: String = ""){
        val user = db.collection(USER_REF).document(userOne)
        val friend = db.collection(USER_REF).document(userTwo)
        val userObject = user.get()
        val friendObject = friend.get()

        Tasks.whenAllSuccess<DocumentSnapshot>(userObject, friendObject)
            .addOnSuccessListener {( userObjectResult, friendObjectResult) ->
                val userObject = userObjectResult.toObject(User::class.java)
                val friendObject = friendObjectResult.toObject(User::class.java)

                if (userObject != null && friendObject != null) {
                    user.collection("conversations").document().set(
                        Conversation(userObject, friendObject, lastConversation)
                    )
                        .addOnSuccessListener {
                            Log.d("CHAT ONE", "TRUE")
                        }
                        .addOnFailureListener { e ->
                            Log.d("CHAT ONE ", e.localizedMessage.toString())
                        }

                    friend.collection("conversations").document().set(
                        Conversation(friendObject, userObject, lastConversation)
                    )
                        .addOnSuccessListener {
                            Log.d("FRIEND CHAT", "TRUE")
                        }
                        .addOnFailureListener { e ->
                            Log.d("FRIEND CHAT", e.localizedMessage.toString())
                        }
                } else {
                    Log.d("CHATS", "CHATS ARE EMPTY")
                }
            }
            .addOnFailureListener { e ->
                Log.d("CHAT ONE", e.localizedMessage.toString())
            }
    }

    suspend fun getUserConversations(uid: String): MutableList<Conversation> {
        val conversationList = mutableStateListOf<Conversation>()
        try {
            val conversationSnapshot = db.collection("users").document(uid).collection("conversations").get().await()

            for (document in conversationSnapshot.documents) {
                val conversation = document.toObject(Conversation::class.java)
                conversation?.let { conversationList.add(it) }
            }
        } catch (e: Exception) {
            Log.d("ERROR", e.toString())
        }
        return conversationList
    }

    suspend fun getAllConversations(uid: String): MutableList<Conversation> {
        val conversationList = mutableListOf<Conversation>()
        try {
            val conversationSnapshot = db.collection("users").document(uid).collection("conversations").get().await()
            for (document in conversationSnapshot.documents) {
                val gson = Gson()
                val userOneNameJson = gson.toJson(document.get("userOne"))
                val userOne = gson.fromJson(userOneNameJson, User::class.java)

                val userTwoNameJson = gson.toJson(document.get("userTwo"))
                val userTwo = gson.fromJson(userTwoNameJson, User::class.java)

                val lastMessageJSON = gson.toJson(document.get("lastMessage"))
                val lastMessage = gson.fromJson(lastMessageJSON, String::class.java)

                conversationList.add(Conversation(userOne, userTwo, lastMessage))
                Log.d("userTwo", conversationList.toString())
            }
        } catch (e: Exception) {
            Log.d("ERROR", e.toString())
        }
        return conversationList
    }
}


//arguments = listOf(navArgument("chatId") { type = NavType.StringType; defaultValue = "chat1234" })