package com.example.hottea.models

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.hottea.AppRoutes
import com.example.hottea.AuthenticationRoutes
import com.example.hottea.repositories.AuthRepository
import com.example.hottea.repositories.FirestoreRepository
import com.example.hottea.screens.HomeScreen
import kotlinx.coroutines.launch


class AuthViewModel (private val authRepository: AuthRepository = AuthRepository()): ViewModel(){
    val currentUser = authRepository.currentUser
    val hasUser: Boolean
        get()= authRepository.hasUser()
    var authUiState by mutableStateOf(RegisterUIState())
        private set
    var loginAuthUiState by mutableStateOf(LoginUiState())
        private set

    fun handleChange(target: String, value: String){
        authUiState = when (target) {
            "name" -> authUiState.copy(name = value)
            "username" -> authUiState.copy(username = value)
            "email" -> authUiState.copy(email = value)
            "password" -> authUiState.copy(password = value)
            "confirmPassword" -> authUiState.copy(confirmPassword = value)

            "loginEmail" -> authUiState.copy(email = value)
            "loginPassword" -> authUiState.copy(password = value)

            else -> authUiState
        }

        loginAuthUiState = when (target) {
            "loginEmail" -> loginAuthUiState.copy(loginEmail = value)
            "loginPassword" -> loginAuthUiState.copy(loginPassword = value)
            else -> loginAuthUiState
        }

    }


    fun registerUser(context: Context) = viewModelScope.launch {
        try {
            if(authUiState.name.isBlank() || authUiState.username.isBlank() || authUiState.email.isBlank()|| authUiState.password.isBlank() || authUiState.confirmPassword.isBlank()){
                authUiState = authUiState.copy(errMsg = "Please fill in all the required fields")
            } else{
                authUiState = authUiState.copy(isLoading = true)

                authRepository.registerUser(authUiState.email,authUiState.password){
                    userId ->
                    if(userId.isNotBlank()){
                        FirestoreRepository().createUserInDatabase(uid = userId, name = authUiState.name, username = authUiState.username, email = authUiState.email, {
                            if(it){
                                authUiState = authUiState.copy(regSucc = true)
                                Toast.makeText(context, "Registration completed", Toast.LENGTH_SHORT).show()

                            } else {
                                authUiState = authUiState.copy(regSucc = false)
                                Toast.makeText(context, "Registration Failed", Toast.LENGTH_SHORT).show()

                            }
                        })
                    } else {
                        Log.d("Error:", "There was an error")
                        Toast.makeText(context, "Registration failed", Toast.LENGTH_SHORT).show()
                        authUiState = authUiState.copy(regSucc = false)
                    }
                }
            }
        }catch (err: Exception){
            Log.d("Error msg", err.localizedMessage.toString())
            err.printStackTrace()
        } finally {
            authUiState = authUiState.copy(isLoading = false)
        }
    }


    fun signInUser(context: Context) = viewModelScope.launch {
        try {
            if (loginAuthUiState.loginEmail.isBlank() || loginAuthUiState.loginPassword.isBlank()) {
                loginAuthUiState = loginAuthUiState.copy(err = "Please fill in all the fields")
            } else {
                loginAuthUiState = loginAuthUiState.copy(isLoading = true)

                authRepository.loginUser(loginAuthUiState.loginEmail, loginAuthUiState.loginPassword) { isCompleted ->
                    if (isCompleted) {
                        Toast.makeText(context, "Login Success!", Toast.LENGTH_SHORT).show()
                        loginAuthUiState = loginAuthUiState.copy(logInSucc = true)

                    } else {
                        Log.d("Error", "There is an error")
                        Toast.makeText(context, "Login failure", Toast.LENGTH_SHORT).show()
                        loginAuthUiState = loginAuthUiState.copy(logInSucc = false)
                    }
                }
            }
        } catch (err: Exception) {
            Log.d("Error Logging in user", err.localizedMessage.toString())
            err.printStackTrace()
        } finally {
            loginAuthUiState = loginAuthUiState.copy(isLoading = false)
        }
    }
}

