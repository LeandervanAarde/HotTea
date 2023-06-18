package com.example.hottea

import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.hottea.models.AuthViewModel
import com.example.hottea.screens.ChatScreen
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
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {
            Log.d("notification request", it.toString())
        }
    )

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
            HomeScreen(
                authViewModel = AuthViewModel,
                navToProfile = {navController.navigate(AppRoutes.Profile.name){
                    launchSingleTop = true
                    popUpTo(AppRoutes.Home.name){
                        inclusive = true
                    }
                }},
                navToConversation = {navController.navigate("${AppRoutes.Chat.name}/${it}"){
                       launchSingleTop = true
                       popUpTo(AppRoutes.Home.name){
                           inclusive = true
                       }
                   } },

                navToLogin =
                {
                    navController.navigate(AuthenticationRoutes.Login.name) {
                        launchSingleTop = true
                        popUpTo(AppRoutes.Home.name) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(route = "${AppRoutes.Chat.name}/{chatId}", arguments = listOf(navArgument("chatId"){type = NavType.StringType; defaultValue = "chatOneTwoThree"})){
            ChatScreen(navBack = {navController.navigate(AppRoutes.Home.name){
                launchSingleTop = true
                popUpTo(AppRoutes.Chat.name){
                    inclusive = true
                }
            } }, chatId = it.arguments?.getString("chatId"))
        }

        composable(route = AppRoutes.Profile.name) {
            ProfileScreen(
                navBack = {
                    navController.navigate(AppRoutes.Home.name) {
                        launchSingleTop = true
                        popUpTo(AppRoutes.Home.name) {
                            inclusive = true
                        }
                    }
                },
                navToRegister = {
                        navController.navigate(AuthenticationRoutes.Login.name) {
                            launchSingleTop = true
                            popUpTo(AuthenticationRoutes.Login.name) {
                                inclusive = true
                            }
                        }
                }
            )
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

    LaunchedEffect(key1 = permissionLauncher){
        Log.d("permission request", "Ye buddy")
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            permissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}