package com.example.hottea.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.hottea.R
import com.example.hottea.ui.theme.Primary
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun OutGoingMessage(text: String, modifier: Modifier = Modifier, image: String, date: Timestamp){
    fun readableDate(date: Timestamp): String {
        val dateFormat = SimpleDateFormat("MMMM dd - hh:mm a", Locale.getDefault())
        val dateMillis = date.toDate().time
        return dateFormat.format(dateMillis)
    }
    Row(
        modifier
            .fillMaxWidth()
            .padding(12.dp, 5.dp)){
        AsyncImage(model = image, contentDescription = null,
            modifier
                .clip(
                    RoundedCornerShape(14.dp)
                )
                .width(70.dp)
                .aspectRatio(1F))

        Spacer(modifier = modifier.size(12.dp))

        Column(modifier.background(color = Primary, shape= RoundedCornerShape(12.dp)).padding(10.dp)) {
            Text(text = text, color = Color.White)
            Spacer(modifier = modifier.size(5.dp))

            Text(text = readableDate(date), color = Color.White, fontSize = 10.sp)
        }
    }
}