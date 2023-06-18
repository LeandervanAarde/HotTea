package com.example.hottea

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hottea.models.AuthViewModel
import com.example.hottea.screens.HomeScreen
import com.example.hottea.screens.LoginScreen
import com.example.hottea.screens.RegisterScreen
import com.example.hottea.services.BackgroundService
import com.example.hottea.ui.theme.HotTeaTheme
import com.google.firebase.FirebaseApp
import com.google.firebase.storage.FirebaseStorage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val authViewModel: AuthViewModel = viewModel(modelClass = AuthViewModel::class.java)
            HotTeaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                  Navigation(AuthViewModel = authViewModel)
//                    HomeScreen()
                }
            }
        }
        startService(Intent(this, BackgroundService::class.java))
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HotTeaTheme {
       Greeting(name = "Android")
    }
}