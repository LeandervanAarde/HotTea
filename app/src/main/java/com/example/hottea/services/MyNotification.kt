package com.example.hottea.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat

class MyNotification (
    private val context: Context,
    private val title: String,
    private val body: String,
) {
    val channelId = "Notification100"
    val channelName = "MyNotification"

    val notificationManager = context.applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    lateinit var notificationChannel: NotificationChannel
    lateinit var notificationBuilder: NotificationCompat.Builder

//
//    fun showNotification(){
//        if(Build.VERSION){
//
//        }
//    }

}