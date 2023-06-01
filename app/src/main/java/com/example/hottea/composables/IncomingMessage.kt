package com.example.hottea.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.hottea.R
import com.example.hottea.ui.theme.Blue

@Composable
fun IncomingMessage(modifier: Modifier = Modifier, text: String){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp, 5.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(end = 12.dp)
        ) {
            Column(
                modifier = Modifier
                    .background(color = Blue, shape = RoundedCornerShape(12.dp))
                    .padding(10.dp)
            ) {
                Text(
                    text = text,
                    color = Color.White
                )
            }
        }
        Image(
            painter = painterResource(id = R.drawable.profileimage),
            contentDescription = null,
            modifier = Modifier
                .width(70.dp)
                .aspectRatio(1f)
                .clip(
                    RoundedCornerShape(14.dp)
                )
        )
    }
}