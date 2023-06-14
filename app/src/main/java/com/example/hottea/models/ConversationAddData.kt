package com.example.hottea.models

data class ConversationData(
    val userOne: String,
    val userTwo: String,
    val lastMessage: String = "",
    var id: String = ""
)