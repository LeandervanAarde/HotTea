package com.example.hottea.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hottea.R
import com.example.hottea.ui.theme.Blue
import com.example.hottea.ui.theme.HotTeaTheme
import com.example.hottea.ui.theme.Primary

@Composable
fun FriendCard(modifier: Modifier = Modifier){
    Column(
        Modifier
            .background(color = Color.Transparent, shape = RoundedCornerShape(12.dp))
            .fillMaxWidth()
            .padding(0.dp, 0.dp, 0.dp, 12.dp)
            ){

        Image(painter = painterResource(id = R.drawable.tester), contentDescription = null,
            modifier
                .fillMaxWidth()
                .background(color = Primary, shape = RoundedCornerShape(12.dp))
                .clip(
                    RoundedCornerShape(12.dp)
                )
        )

        Column(modifier.padding(12.dp, 0.dp)) {
            Text(text = "Leander van Aarde", color = Color.White, fontSize= 18.sp, fontWeight = FontWeight.Medium, modifier = Modifier.padding(0.dp, 2.5.dp))

            Row(modifier = Modifier.padding(0.dp, 2.5.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier
                    .size(10.dp)
                    .clip(shape = CircleShape)
                    .background(Color.Green)){

                }
                Spacer(modifier = Modifier.size(18.dp))
                Text(text = "Available",   color = Color.Green, fontSize= 12.sp)
            }

            Row(
                modifier
                    .fillMaxWidth()
                    .padding(0.dp, 3.dp), horizontalArrangement = Arrangement.Center) {
                PrimaryButton(color = Blue, icon = ImageVector.vectorResource(id = R.drawable.ic_chat), text = "Start Chatting" ) {

                }
            }
        }
    }
}
