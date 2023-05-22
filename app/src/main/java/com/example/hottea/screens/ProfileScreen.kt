package com.example.hottea.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.hottea.R
import com.example.hottea.ui.theme.Blue
import com.example.hottea.ui.theme.HotTeaTheme
import com.example.hottea.ui.theme.Primary

@Composable
fun ProfileScreen(modifier: Modifier = Modifier){
    val checked = remember{ mutableStateOf(true) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Primary)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(310.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Transparent)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.profileimage),
                        modifier = Modifier
                            .fillMaxSize()
                            .blur(radius = 10.dp)
                        ,
                        colorFilter = ColorFilter.tint(Color.Gray, blendMode = BlendMode.Multiply),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Content of the inner column goes here
                        Box(
                            modifier = Modifier
                                .background(Blue, shape = CircleShape)
                                .size(170.dp)
                                .padding(5.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.profileimage),
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape)

                                ,
                                contentDescription = null,
                                contentScale = ContentScale.Crop
                            )
                        }

                        Spacer(modifier = Modifier.size(12.dp))

                        Text(text = "Rubb3rDuck_26", color = Color.White)
                        Spacer(modifier = Modifier.size(7.dp))
                        Text(text = "I just love cheese and De..." ,  color = Color.White)
                    }
                }
            }
        }

        Column(
            modifier
                .fillMaxSize()
                .padding(15.dp)) {

            Text(text = "General", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight(700))

            Row(Modifier.padding(0.dp, 15.dp)) {

                Column(
                    Modifier
                        .width(150.dp)
                        .height(150.dp)) {
                    Image(painter = painterResource(id = R.drawable.profileimage), contentDescription = null,
                        modifier
                            .size(100.dp)
                            .clip(
                                RoundedCornerShape(12.dp)
                            )
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(text = "Tap to change", color = Color.Gray, fontSize = 14.sp)
                }

                Row( verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Light", color = Color.Gray, fontSize = 12.sp)
                    Spacer(modifier = Modifier.size(10.dp))

                    Switch(checked = checked.value, onCheckedChange = {checked.value = it})
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(text = "Dark", color = Color.Gray, fontSize = 12.sp)
                }

            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun previewProfileScreen(){
    HotTeaTheme {
        ProfileScreen()
    }
}