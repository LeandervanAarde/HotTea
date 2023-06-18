package com.example.hottea.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.hottea.MainActivity
import com.example.hottea.R

class MyNotification (
    private val context: Context,
    private val title: String,
    private val body: String,
) {
    val channelId = "Notification100"
    val channelName = "MyNotification"

    val notificationManager = context.applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    lateinit var notificationChannel: NotificationChannel
    lateinit var notificationBuiler: NotificationCompat.Builder

    fun showNotification(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            )

            notificationManager.createNotificationChannel(notificationChannel)
        }

        val intent = Intent(context, MainActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        notificationBuiler = NotificationCompat.Builder(context, channelId)
        notificationBuiler.setSmallIcon(R.mipmap.ic_launcher_round)
        notificationBuiler.setContentTitle(title)
        notificationBuiler.setContentText(body)
        notificationBuiler.setAutoCancel(true)
        notificationBuiler.setContentIntent(pendingIntent)

        //checking to see if we actually have permission
        with(NotificationManagerCompat.from(context)){
            if(ActivityCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED) {
                Log.d("AAA Notification permission", "DO NOT HAVE PERMISSION TO SHOW")
            }
            notify(100, notificationBuiler.build())
        }
    }

}