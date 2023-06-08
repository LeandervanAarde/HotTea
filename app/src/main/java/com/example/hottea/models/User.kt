package com.example.hottea.models

data class User(
    val id: String = "",
    val name: String ="",
    val username: String = "",
    val email: String = "",
    val profileImage: String = "",
    val status: String = "",
    val friends: List<String> = emptyList(),
    val chats: List<String> = emptyList()
)
