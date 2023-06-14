package com.example.hottea.models

data class Conversation(
    val userOne: User,
    val userTwo: User,
    val lastMessage: String,
    var id: String = "",
)
