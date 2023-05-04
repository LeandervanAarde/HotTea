package com.example.hottea.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.hottea.ui.theme.HotTeaTheme

@Composable
fun RegisterScreen(modifier: Modifier = Modifier){
    Column(modifier = Modifier.fillMaxSize().background(brush = Brush.verticalGradient(
        listOf(Color.Black, Color.White)
    ))) {

    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewRegisterScreen(){
    HotTeaTheme {
        RegisterScreen()
    }
}