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
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hottea.R
import com.example.hottea.ui.theme.HotTeaTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import com.example.hottea.composables.GoogleButton
import com.example.hottea.composables.Input
import com.example.hottea.composables.PrimaryButton
import com.example.hottea.models.AuthViewModel
import com.example.hottea.ui.theme.Blue
import com.example.hottea.ui.theme.Green
import com.example.hottea.ui.theme.gradient

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(authViewModel: AuthViewModel, modifier: Modifier = Modifier, navigatetoLogin: () -> Unit){

    val authUiState = authViewModel?.authUiState
    val err = authUiState?.errMsg != null
    val context = LocalContext.current

    Column(modifier = Modifier
        .fillMaxSize()
        .background(
            gradient
        ), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(30.dp, 10.dp, 30.dp, 0.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = painterResource(id = R.drawable.logo), contentDescription = "Hot Tea Logo", contentScale = ContentScale.Fit, modifier = Modifier.size(120.dp) )

                Spacer(modifier = Modifier.size(10.dp))

            if(err){
                Spacer(modifier = Modifier.size(10.dp))
                Text(text = "Please ensure that all fields are filled in" , modifier = Modifier.padding(13.dp, 0.dp), color = Color.Red, fontSize= 14.sp)
                Spacer(modifier = Modifier.size(10.dp))
            }
            Input(
                textValue = authUiState?.name ?: "",
                changedVal = { newText -> authViewModel.handleChange("name", newText)},
                label ="Name" ,
                icon = Icons.Default.Person,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text) ,
                transformation = VisualTransformation.None
            )


            Spacer(modifier = Modifier.size(10.dp))

            Input(
                textValue = authUiState?.username ?: "",
                changedVal = { newText -> authViewModel.handleChange("username", newText)},
                label ="Username" ,
                icon = Icons.Default.AccountCircle,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text) ,
                transformation = VisualTransformation.None
            )
            Spacer(modifier = Modifier.size(10.dp))

            Input(
                textValue = authUiState?.email ?: "",
                changedVal = { newText -> authViewModel.handleChange("email", newText)},
                label ="Email" ,
                icon = Icons.Default.Email,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email) ,
                transformation = VisualTransformation.None
            )

            Spacer(modifier = Modifier.size(10.dp))

            Input(
                textValue = authUiState?.password ?: "",
                changedVal = { newText -> authViewModel.handleChange("password", newText)},
                label = "Password" ,
                icon =  Icons.Default.Lock ,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                transformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.size(10.dp))

            Input(
                textValue = authUiState?.confirmPassword ?: "",
                changedVal = { newText -> authViewModel.handleChange("confirmPassword", newText)},
                label = "Confirm password" ,
                icon =  Icons.Default.Lock ,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                transformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.size(30.dp))
            PrimaryButton(color = Blue, icon = Icons.Default.Lock, text = "Register account" ){
                authViewModel?.registerUser(context = context)
            }



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
            Column(modifier = Modifier, verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text ="Already have an account?" , modifier = Modifier.padding(13.dp, 0.dp), color = Color.White, fontSize= 12.sp)
                TextButton(onClick = {navigatetoLogin.invoke()}) {
                    Text(text = "Log in")
                }
            }
        }
    }
}
@Preview(showSystemUi = true)
@Composable
fun PreviewRegisterScreen(){
    HotTeaTheme {
        RegisterScreen(navigatetoLogin = {}, authViewModel = AuthViewModel())
    }
}
