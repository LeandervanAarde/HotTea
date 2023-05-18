package com.example.hottea

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hottea.screens.LoginScreen
import com.example.hottea.screens.RegisterScreen

enum class AuthenticationRoutes {
    Login,
    Register
}

@Composable
fun Navigation(navController: NavHostController = rememberNavController()){
    NavHost(navController = navController, startDestination = AuthenticationRoutes.Login.name){
        composable(route = AuthenticationRoutes.Login.name){
            LoginScreen(navigateToRegister = {navController.navigate(AuthenticationRoutes.Register.name){
                launchSingleTop = true
                popUpTo(route = AuthenticationRoutes.Login.name){
                    inclusive = true
                }
            }})
        }

        composable(route = AuthenticationRoutes.Register.name){
            RegisterScreen(navigatetoLogin = {navController.navigate(AuthenticationRoutes.Login.name){
                launchSingleTop = true
                popUpTo(AuthenticationRoutes.Login.name){
                    inclusive = true
                }
            } })
        }
    }
}