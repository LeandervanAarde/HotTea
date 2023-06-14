package com.example.hottea.models

import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.MutableStateFlow
import java.text.SimpleDateFormat

data class Message(
    val userId: String = "",
    val time: com.google.firebase.Timestamp = com.google.firebase.Timestamp.now() ,
    val message: String =""
)
