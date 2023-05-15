package com.example.hottea.composable

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun PrimaryButton(color: Color, icon: ImageVector, text: String){
    Button(onClick = { Log.i("hello", "Hey there!")}, modifier = Modifier.height(40.dp), colors = ButtonDefaults.buttonColors(
        color
    )) {
        Icon(imageVector = icon, contentDescription = null )
        Spacer(modifier = Modifier.size(7.dp))
        Text(text = text, modifier = Modifier.padding(0.dp, 3.dp), fontSize = 11.sp)
    }
}

@Composable
fun GoogleButton(color: Color, image: Int, text: String){
    Button(onClick = { /*TODO*/ }, modifier = Modifier.height(40.dp), colors = ButtonDefaults.buttonColors(
        color
    )) {
        Image(painter = painterResource(id = image), contentDescription = null, contentScale = ContentScale.Fit, modifier = Modifier.size(20.dp) )
        Spacer(modifier = Modifier.size(7.dp))
        Text(text = text, modifier = Modifier.padding(0.dp, 3.dp))
    }
}



