package com.example.hottea.repositories

import android.net.Uri
import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class FirebaseStorageRepository {
    val storageRef = Firebase.storage.reference

    suspend fun uploadImage(imageUri: Uri, fileName: String, onSuccess: (downloadUrl: String) -> Unit){
        try {
            val downloadUrl = storageRef.child("profiles/$fileName")
                .putFile(imageUri).await()
                .storage.downloadUrl.await()
            onSuccess(downloadUrl.toString())
        }
        catch (e: Exception){
            Log.d("ERR", e.localizedMessage)
            e.printStackTrace()
            onSuccess("")
        }
    }
}