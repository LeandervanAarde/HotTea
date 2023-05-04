package com.example.hottea.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hottea.R
import com.example.hottea.ui.theme.HotTeaTheme
import com.example.hottea.ui.theme.Primary
import com.example.hottea.ui.theme.PrimaryLight
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.example.hottea.ui.theme.Blue
import com.example.hottea.ui.theme.Green

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(modifier: Modifier = Modifier){

    val gradient = Brush.verticalGradient(
        0.0f to PrimaryLight,
        1.0f to Primary,
        startY = -1000.0f,
        endY = 1000.0f
    )

    var name by remember {
        mutableStateOf("")
    }

    var email by remember {
        mutableStateOf("")
    }

    var username by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var confirmPassword by remember {
        mutableStateOf("")
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(
            gradient
        ), horizontalAlignment = Alignment.CenterHorizontally) {

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(30.dp, 10.dp, 30.dp, 0.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Image(painter = painterResource(id = R.drawable.logo), contentDescription = "Hot Tea Logo", contentScale = ContentScale.Fit, modifier = Modifier.size(80.dp) )

                Spacer(modifier = Modifier.size(10.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(color = Color.White),
                    value = name,
                    onValueChange = {name = it},
                    label = { Text (text = "Name") },
                    leadingIcon = {Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null)},
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                )
            Spacer(modifier = Modifier.size(10.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = Color.White),
                value = username,
                onValueChange = {username = it},
                label = { Text (text = "Username") },
                leadingIcon = {Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null)},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),

            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = Color.White),
                value = email,
                onValueChange = {email = it},
                label = { Text (text = "Email") },
                leadingIcon = {Icon(imageVector = Icons.Default.Email, contentDescription = null)},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            )
            Spacer(modifier = Modifier.size(10.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = Color.White),
                value = password,
                onValueChange = {password = it},
                label = { Text (text = "Password") },
                leadingIcon = {Icon(imageVector = Icons.Default.Lock, contentDescription = null)},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.size(10.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = Color.White),
                value = confirmPassword,
                onValueChange = {confirmPassword = it},
                label = { Text (text = "Confirm your password") },
                leadingIcon = {Icon(imageVector = Icons.Default.Lock, contentDescription = null)},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation()
            )

            Row(modifier= Modifier
                .fillMaxWidth()
                .padding(20.dp, 10.dp), verticalAlignment = Alignment.CenterVertically) {
                Divider(color = Color.White, thickness = 1.dp, modifier = Modifier.width(125.dp))
                Text(text = "OR", color = Color.White, modifier = Modifier.padding(10.dp))
                Divider(color = Color.White, thickness = 1.dp,  modifier = Modifier.width(145.dp))
            }

            Button(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(Blue)) {
                Icon(imageVector = Icons.Default.Lock, contentDescription = null)
                Spacer(modifier = Modifier.size(7.dp))
                Text(text = "Register an account", modifier = Modifier.padding(0.dp, 10.dp))
            }

            Spacer(modifier = Modifier.size(30.dp))

            Button(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(
                Green)) {
                Image(painter = painterResource(id = R.drawable.googlelogin), contentDescription = null, contentScale = ContentScale.Fit, modifier = Modifier.size(20.dp) )
                Spacer(modifier = Modifier.size(7.dp))
                Text(text = "Log in with Google", modifier = Modifier.padding(0.dp, 10.dp))
            }
        }

    }
}
@Preview(showSystemUi = true)
@Composable
fun PreviewRegisterScreen(){
    HotTeaTheme {
        RegisterScreen()
    }
}