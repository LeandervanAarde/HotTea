package com.example.hottea.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hottea.composable.HeaderProfile
import com.example.hottea.ui.theme.HotTeaTheme
import com.example.hottea.ui.theme.gradient
import com.example.hottea.composable.PrimaryButton
import com.example.hottea.ui.theme.Primary

@Composable
fun ChatsScreen(modifier: Modifier = Modifier){

    Column( modifier = Modifier
        .fillMaxSize()
        .background(gradient)) {
        HeaderProfile()

        Column(modifier.fillMaxSize().background(Primary).shadow(3.dp).clip(shape = RoundedCornerShape(30.dp))) {

        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun PreviewChatsScreen(){
    HotTeaTheme {
        ChatsScreen()
    }
}

