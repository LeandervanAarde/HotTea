package com.example.hottea.composables

import android.provider.CalendarContract.Colors
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hottea.R
import com.example.hottea.models.TopNavItem
import com.example.hottea.ui.theme.Blue

import com.example.hottea.ui.theme.HotTeaTheme
import com.example.hottea.ui.theme.Primary
import com.example.hottea.ui.theme.PrimaryLight
import com.example.hottea.ui.theme.gradient

@Composable
fun ConverSationItem(modifier: Modifier= Modifier, navigate: () -> Unit){
    Column(
        modifier
            .fillMaxWidth()
            .padding(10.dp, 5.dp)
            .clickable{navigate}
            .background(Primary, shape = RoundedCornerShape(20.dp))
            .height(80.dp)) {
       Row(
           modifier
               .fillMaxWidth()
               .fillMaxHeight()
               .clickable{navigate()}
               .padding(10.dp)
               .background(color = Primary), verticalAlignment = Alignment.CenterVertically) {
          Column(
              modifier
                  .width(70.dp)
                  .height(70.dp)
                  .background(Primary, shape = RoundedCornerShape(20.dp))) {
              Image(painter = painterResource(id = R.drawable.profileimage), contentDescription = null,
                  modifier
                      .clip(shape = RoundedCornerShape(15.dp))
                      .height(70.dp))
          }

           Column(
               modifier
                   .width(250.dp)
                   .fillMaxHeight()) {

               Text(text = "Username goes here", fontSize = 13.sp, color = Color.White, fontWeight = FontWeight(700))
               Spacer(modifier = Modifier.size(5.dp))
               Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {

                   Column(modifier = Modifier
                       .size(9.dp)
                       .clip(shape = CircleShape)
                       .background(Color.Green)){

                   }
                   Spacer(modifier = Modifier.size(18.dp))
                   Text(text = "Available",   color = Color.Green, fontSize= 9.sp)
               }

               Spacer(modifier = Modifier.size(5.dp))

               Text(text = "This is the last message that someone...", fontSize = 9.sp, color = Color.White)
               
           }

           Column(
               modifier
                   .width(100.dp)
                   .fillMaxHeight()
                   .padding(5.dp), verticalArrangement = Arrangement.SpaceBetween, horizontalAlignment = Alignment.CenterHorizontally) {

                Column(modifier.width(20.dp).height(20.dp).background(Blue, shape = CircleShape), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "10", fontSize = 9.sp, color = Color.White)
                }
//               Spacer(modifier = Modifier.size(12.dp))
               Text(text = "12:36", fontSize = 9.sp, color = Color.White)
           }
       }
    }
}


@Preview(showSystemUi = true)
@Composable
fun PreviewLoginScreen(){
    HotTeaTheme {
        ConverSationItem(navigate = {})
    }
}