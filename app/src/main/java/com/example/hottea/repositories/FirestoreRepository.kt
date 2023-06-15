package com.example.hottea.repositories

import android.net.Uri
import android.util.Log
import com.example.hottea.models.Conversation
import com.example.hottea.models.ConversationData
import com.example.hottea.models.Message
import com.example.hottea.models.User
import com.google.android.gms.tasks.Tasks
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.google.gson.Gson
import kotlinx.coroutines.tasks.await

//import kotlin.coroutines.jvm.internal.CompletedContinuation.context

const val USER_REF = "users"
const val CONVO_REF = "conversations"


class FirestoreRepository {
    val db = Firebase.firestore
    val storage = Firebase.storage
    val userRef = db.collection((USER_REF))
    val userId = AuthRepository()
    var storageRef = storage.reference
    var profileImageRef = storageRef.child("/profileImages")
    val conversationRef = db.collection((CONVO_REF))

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

     suspend fun updateUserData(uid: String, username: String, status: String, newImage: Uri?){
        var downloadUrl : String = ""

        if(newImage != null){
            uploadImage(newImage, username){
                downloadUrl = it.toString()
            }
        }
        userRef.document(uid)
            .update("username", username, "status", status, "profileImage", downloadUrl?: "https://firebasestorage.googleapis.com/v0/b/hottea-44cd0.appspot.com/o/profileImage.jpg?alt=media&token=ea6a630a-3dd0-4762-9509-1c91c9fd16dc&_gl=1*iq0cin*_ga*MTg3MzQ4NjU1LjE2ODI4NDIwMTg.*_ga_CW55HF8NVT*MTY4NjM5Mzg4MC42My4xLjE2ODYzOTQwOTAuMC4wLjA.")
            .addOnSuccessListener {

                Log.d("UPDATE", "DocumentSnapshot successfully updated!")
            }
            .addOnFailureListener { e -> Log.w("UPDATE", "Error updating document", e) }
    }

    suspend fun uploadImage(imageUri: Uri, fileName: String, onSuccess: (downloadUrl: String) -> Unit){
        try {
            val downloadUrl = storageRef.child("profiles/$fileName")
                .putFile(imageUri).await()
                .storage.downloadUrl.await()
            onSuccess(downloadUrl.toString())
        }
        catch (e: java.lang.Exception){
            Log.d("ERR", e.localizedMessage)
            e.printStackTrace()
            onSuccess("")
        }
    }
    fun addFriend(email: String, uid: String){
        // Get the user document with the specified email address.
        val user = db.collection(USER_REF).document(uid)
        val friendDoc = db.collection(USER_REF).whereEqualTo("email", email)
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

    fun createNewConversation(userOne: String, userTwo: String, lastConversation: String = "", navToConversation: (chatId: String) -> Unit) {
        val user = db.collection(USER_REF).document(userOne)
        val friend = db.collection(USER_REF).document(userTwo)
        val userObject = user.get()
        val friendObject = friend.get()


        Tasks.whenAllSuccess<DocumentSnapshot>(userObject, friendObject)
            .addOnSuccessListener { (userObjectResult, friendObjectResult) ->
                val userObject = userObjectResult.toObject(User::class.java)
                val friendObject = friendObjectResult.toObject(User::class.java)

                if (userObject != null && friendObject != null) {
                    conversationRef
                        .whereEqualTo("userOne", userOne)
                        .whereEqualTo("userTwo", userTwo)
                        .get()
                        .addOnSuccessListener {
                            if(it.isEmpty){
                                var conversationData = ConversationData(userOne, userTwo, lastConversation)
                                conversationRef.add(conversationData)
                                    .addOnSuccessListener { conversationDocRef ->
                                        val conversationId = conversationDocRef.id
                                        conversationData.id = conversationId

                                        conversationDocRef.update("id", conversationId)

                                        Log.d("CHAT", "Conversation document created with ID: $conversationId")

                                        val messagesCollection = conversationRef
                                            .document(conversationId)
                                            .collection("messages")

                                        val messageData = Message("", Timestamp.now(), "")

                                        messagesCollection.add(messageData)
                                            .addOnSuccessListener {
                                                Log.d("CHAT", "Message document created")
                                                navToConversation(conversationId)
                                            }
                                            .addOnFailureListener { e ->
                                                Log.d("CHAT", "Failed to create message document: ${e.localizedMessage}")
                                            }
                                    }
                                    .addOnFailureListener { e ->
                                        Log.d("CHAT", "Failed to create conversation document: ${e.localizedMessage}")
                                    }

                            } else {
                                val existingConversation = it.documents.first()
                                val conversationId = existingConversation.id
                                navToConversation(conversationId)
                            }

                        }
                        .addOnFailureListener {
                            Log.d("ERR", it.localizedMessage)
                        }
                }
            }
            .addOnFailureListener { e ->
                Log.d("CHAT", "Failed to fetch user or friend object: ${e.localizedMessage}")
                }
            }



    suspend fun getAllConversations(uid: String): MutableList<Conversation> {
        val conversationList = mutableListOf<Conversation>()
        try {
            val query1 = db.collection("conversations").whereEqualTo("userOne", uid)
            val query2 = db.collection("conversations").whereEqualTo("userTwo", uid)

            val query1Task = query1.get()
            val query2Task = query2.get()

            val snapshotList = Tasks.whenAllSuccess<QuerySnapshot>(listOf(query1Task, query2Task)).await()

            for (docs in snapshotList) {
                for (document in docs) {
                    val userOneRef = db.collection("users").document(document.getString("userOne")!!).get().await()
                    val userTwoRef = db.collection("users").document(document.getString("userTwo")!!).get().await()

                    val userOne = userOneRef.toObject(User::class.java)!!
                    val userTwo = userTwoRef.toObject(User::class.java)!!

                    val gson = Gson()

                    val lastMessageJSON = gson.toJson(document.get("lastMessage"))
                    val lastMessage = gson.fromJson(lastMessageJSON, String::class.java)

                    val idJSON = gson.toJson(document.get("id"))
                    val id = gson.fromJson(idJSON, String::class.java)

                    conversationList.add(Conversation(userOne, userTwo, lastMessage, id))
                }
            }
            Log.d("userTwoYAY", conversationList.toString())
        } catch (e: Exception) {
            Log.d("userTwoYAY", e.toString())
        }
        return conversationList
    }
    suspend fun addNewMessage(
        msg: Message,
        chatId: String,
        onSuccess: (Boolean) -> Unit
    ){
        db.collection("conversations").document(chatId).collection("messages")
            .add(msg)
            .addOnSuccessListener {
                Log.d("MSG Sent", it.id)
                onSuccess.invoke(true)

                db.collection("conversations").document(chatId)
                    .update("lastMessage", msg.message)
            }
            .addOnFailureListener { e ->
                Log.d("ERR", e.localizedMessage)
                onSuccess.invoke(false)
            }.await()
    }
}




//arguments = listOf(navArgument("chatId") { type = NavType.StringType; defaultValue = "chat1234" })