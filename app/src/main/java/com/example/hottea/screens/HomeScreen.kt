package com.example.hottea.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.hottea.composables.PrimaryButton
import com.example.hottea.composables.ProfileHeader
import com.example.hottea.ui.theme.Blue
import com.example.hottea.ui.theme.Primary
import com.example.hottea.ui.theme.PrimaryLight
import com.example.hottea.ui.theme.Red
import com.example.hottea.ui.theme.gradient

@Composable
fun HomeScreen(modifier: Modifier = Modifier){
    Column(modifier = Modifier
        .fillMaxSize()
        .background(gradient)) {
        Column {
            ProfileHeader()
            Row(modifier = Modifier.fillMaxWidth().padding(0.dp, 0.dp, 12.dp, 0.dp), horizontalArrangement = Arrangement.End) {
                PrimaryButton(color = Blue, icon = Icons.Default.Person , text = "Profile" )
                Spacer(modifier = Modifier.size(12.dp))
                PrimaryButton(color = Red, icon = Icons.Default.ExitToApp , text = "LogOut" )
            }
        }
        Spacer(modifier = Modifier.size(12.dp))

        Column(modifier = Modifier
            .fillMaxSize()
            .clip(shape = RoundedCornerShape(20.dp))
            .shadow(elevation = 10.dp)

            .background(PrimaryLight, shape = RoundedCornerShape(30.dp, 30.dp, 0.dp, 0.dp))){
        }
    }
}