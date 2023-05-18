package com.example.hottea.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hottea.composables.GoogleButton
import com.example.hottea.composables.Input
import com.example.hottea.composables.PrimaryButton
import com.example.hottea.R
import com.example.hottea.ui.theme.Blue
import com.example.hottea.ui.theme.Green
import com.example.hottea.ui.theme.HotTeaTheme
import com.example.hottea.ui.theme.gradient



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen (modifier: Modifier = Modifier, navigateToRegister: () -> Unit){


    var username by remember {
        mutableStateOf("")
    }

    val userNameUpdate = { data: String ->
        username = data
    }

    var password by remember {
        mutableStateOf("")
    }

    fun checkValues (){
        Log.i("username", username)
        Log.i("password", password)
    }



    Column(modifier = Modifier
        .fillMaxSize()
        .background(
            gradient
        ), horizontalAlignment = Alignment.CenterHorizontally) {

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(30.dp, 10.dp, 30.dp, 0.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Image(painter = painterResource(id = R.drawable.logo), contentDescription = "Hot Tea Logo", contentScale = ContentScale.Fit, modifier = Modifier.size(120.dp) )
            Spacer(modifier = Modifier.size(15.dp))
            Input(
                textValue = username,
                changedVal = {newText -> username = newText},
                label = "Username" ,
                icon = Icons.Default.Person,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                transformation = VisualTransformation.None
            )

            Spacer(modifier = Modifier.size(15.dp))

            Input(
                textValue = password,
                changedVal = {newText -> password = newText},
                label = "Password" ,
                icon =  Icons.Default.Lock ,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                transformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.size(30.dp))

            PrimaryButton(color = Blue, icon = Icons.Default.Person , text = "Log in" )

            Spacer(modifier = Modifier.size(30.dp))

            GoogleButton(color = Green, image = R.drawable.googlelogin, text = "Login with Google")

            Spacer(modifier = Modifier.size(15.dp))
            Row(modifier= Modifier
                .fillMaxWidth()
                .padding(20.dp, 10.dp), verticalAlignment = Alignment.CenterVertically) {
                Divider(color = Color.White, thickness = 1.dp, modifier = Modifier.width(125.dp))
                Text(text = "OR", color = Color.White, modifier = Modifier.padding(10.dp))
                Divider(color = Color.White, thickness = 1.dp,  modifier = Modifier.width(145.dp))
            }

            Column(modifier = Modifier, verticalArrangement = Arrangement.Center) {
                Text(text = "New To Hot Tea?", modifier = Modifier.padding(13.dp, 0.dp), color = Color.White, fontSize= 12.sp)
                TextButton(onClick = {navigateToRegister.invoke()}) {
                    Text(text = "Register account")
                }
            }
        }
    }
}
@Preview(showSystemUi = true)
@Composable
fun PreviewLoginScreen(){
    HotTeaTheme {
        LoginScreen(navigateToRegister = {})
    }
}
