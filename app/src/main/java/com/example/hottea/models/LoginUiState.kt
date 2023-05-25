package com.example.hottea.models

data class LoginUiState (
    val isLoading: Boolean = false,
    val err: String? = null,
    val logInSucc: Boolean = false,
    val loginEmail: String = "",
    val loginPassword: String = ""
    )

