package com.example.hottea.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hottea.R
import com.example.hottea.ui.theme.Blue
import com.example.hottea.ui.theme.PrimaryLight

@Composable
fun ProfileHeader(){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)){

        Column(modifier = Modifier
            .size(120.dp)
            .clip(shape = CircleShape)
            .border(width = 4.dp, color = Blue, shape = CircleShape)){

            Image(painter = painterResource(id = R.drawable.profileimage), contentDescription = null, contentScale = ContentScale.Crop)
        }
        Spacer(modifier = Modifier.size(10.dp))

        Column(modifier = Modifier
            .fillMaxWidth()){

            Row(modifier = Modifier.padding(12.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                Image(painter = painterResource(id = R.drawable.logo), contentDescription = null, contentScale = ContentScale.Fit, modifier = Modifier.size(30.dp) )
                Spacer(modifier = Modifier.size(12.dp))
                Text(text = "Leander van Aarde",   color = Color.White, fontSize= 18.sp, fontWeight = FontWeight.Medium)
            }

            Row(modifier = Modifier.padding(20.dp, 0.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier
                    .size(10.dp)
                    .clip(shape = CircleShape)
                    .background(Color.Green)){

                }
                Spacer(modifier = Modifier.size(18.dp))
                Text(text = "Available",   color = Color.Green, fontSize= 12.sp)
            }

            Row(modifier = Modifier.padding(12.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                Image(imageVector = Icons.Default.Edit , contentDescription = null, modifier = Modifier.size(25.dp), colorFilter = ColorFilter.tint(Color.White) )
                Spacer(modifier = Modifier.size(12.dp))
                Text(text = "I just like to eat cheese...",   color = Color.Gray, fontSize= 12.sp)
            }
//            Row(modifier = Modifier
//                .fillMaxWidth()
//                .height(60.dp)) {
//                PrimaryButton(color = Blue, icon = Icons.Default.Person, text = "Profile")
//                Spacer(modifier = Modifier.size(10.dp))
//                PrimaryButton(color = Red, icon = Icons.Default.ExitToApp, text = "Logout")
//            }
        }

    }


}