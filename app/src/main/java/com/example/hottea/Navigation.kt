package com.example.hottea

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hottea.models.AuthViewModel
import com.example.hottea.screens.HomeScreen
import com.example.hottea.screens.LoginScreen
import com.example.hottea.screens.ProfileScreen
import com.example.hottea.screens.RegisterScreen

enum class AuthenticationRoutes {
    Login,
    Register
}

enum class AppRoutes {
    Home,
    Profile,
    Chat
}
@Composable
fun Navigation(navController: NavHostController = rememberNavController(), AuthViewModel: AuthViewModel){
   val initialRoute =  if(AuthViewModel.hasUser){
        AppRoutes.Home.name
    } else {
        AuthenticationRoutes.Login.name
    }

    NavHost(navController = navController, startDestination = initialRoute){
        composable(route = AuthenticationRoutes.Login.name){
    LoginScreen(
        navigateToRegister = {
            navController.navigate(AuthenticationRoutes.Register.name) {
                launchSingleTop = true
                popUpTo(route = AuthenticationRoutes.Login.name) {
                    inclusive = true
                }
            }
        },
        navigateHome = {
            navController.navigate(AppRoutes.Home.name) {
                launchSingleTop = true
                popUpTo(AuthenticationRoutes.Login.name) {
                    inclusive = true
                }
            }
        },
        authViewModel = AuthViewModel()
    )
        }

        composable(route = AppRoutes.Home.name){
            HomeScreen(authViewModel = AuthViewModel, navToProfile = {navController.navigate(AppRoutes.Profile.name){
                launchSingleTop = true
                popUpTo(AppRoutes.Home.name){
                    inclusive = true
                }
            }})
        }

        composable(route = AppRoutes.Profile.name){
            ProfileScreen()
        }

        composable(route = AuthenticationRoutes.Register.name){
            RegisterScreen(navigatetoLogin = {navController.navigate(AuthenticationRoutes.Login.name){
                launchSingleTop = true
                popUpTo(AuthenticationRoutes.Register.name){
                    inclusive = true
                }
            } }, navToHome = {
                navController.navigate(AppRoutes.Home.name){
                    launchSingleTop = true
                    popUpTo(route = AuthenticationRoutes.Register.name){
                        inclusive = true
                    }
                }
            }, authViewModel = AuthViewModel()  )
        }
    }
}