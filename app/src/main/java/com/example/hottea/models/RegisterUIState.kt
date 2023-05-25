package com.example.hottea.models

data class RegisterUIState(
    val isLoading: Boolean = false,
    val errMsg: String? = null,
    val regSucc: Boolean = false,
    val name: String = "",
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = ""
)
