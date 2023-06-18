package com.example.hottea.services
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.hottea.models.Message
import com.example.hottea.repositories.AuthRepository
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.core.View.DocumentChanges
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BackgroundService: Service() {

    var onMessageListener: ListenerRegistration? = null

    var authRepository = AuthRepository()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Thread{
            while (true){
                try {

                    Log.d("AAA Service, ", "running...")

                    if(authRepository.hasUser()){
                        Log.d("AAA service", "user has logged in!")
                        if(onMessageListener == null){
                            startFirestoreListener()
                        } else {
                            Log.e("AAA Service, ", "already listening")
                        }
                    } else {
                        Log.d("AAA service", "user NOT logged in!")
                    }

                    Thread.sleep(15000)
                } catch (e: Exception){
                    onMessageListener = null
                    e.printStackTrace()

                    Log.d("ERROR HERE", e.localizedMessage)
                }
            }
        }.start()

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        onMessageListener = null
        Log.d("AAA Service,", "destoyed...")
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun startFirestoreListener() {
        val collectionRef = Firebase.firestore
            .collectionGroup("messages")
            .orderBy("time", Query.Direction.DESCENDING)

        onMessageListener = collectionRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.d("AAA listener went wrong", e.localizedMessage ?: "")
                return@addSnapshotListener
            }

            for (dc in snapshot?.documentChanges ?: emptyList()) {
                if (dc.type == DocumentChange.Type.ADDED) {
                    val userId = dc.document.getString("userId")
                    val currentUser = authRepository.currentUser?.uid

                    if (userId != null && currentUser != userId) {
                        val message = dc.document.getString("message")
                        if (message != null) {
                            MyNotification(
                                this,
                                "New Message",
                                message
                            ).showNotification()
                        }
                    }
                }
            }
        }
    }
}