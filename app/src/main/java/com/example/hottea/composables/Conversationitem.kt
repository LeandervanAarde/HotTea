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
fun ConversationItem(modifier: Modifier= Modifier, navigate: (String) -> Unit, username: String, lastMessage: String, id: String){
    Column(
        modifier
            .fillMaxWidth()
            .padding(10.dp, 7.dp)
            .clickable { navigate(id)}
            .background(Primary, shape = RoundedCornerShape(20.dp))
            .height(88.dp)) {
       Row(
           modifier
               .fillMaxWidth()
               .fillMaxHeight()
               .clickable { navigate(id) }
               .padding(10.dp)
               .background(color = Primary), verticalAlignment = Alignment.CenterVertically) {
          Column(
              modifier
                  .width(70.dp)
                  .height(70.dp)
                  .background(Primary, shape = RoundedCornerShape(20.dp)),
          horizontalAlignment = Alignment.CenterHorizontally) {
              Image(painter = painterResource(id = R.drawable.logo), contentDescription = null,
                  modifier
                      .clip(shape = RoundedCornerShape(15.dp))
                      .height(70.dp))
          }
           Spacer(modifier = modifier.size(6.dp))
           Column(
               modifier
                   .width(250.dp)
                   .fillMaxHeight(),
           verticalArrangement = Arrangement.Center) {

               Text(text = username, fontSize = 13.sp, color = Color.White, fontWeight = FontWeight(700))
               Spacer(modifier = Modifier.size(6.dp))


               Text(text = lastMessage, fontSize = 9.sp, color = Color.White)
               
           }
       }
    }
}


//@Preview(showSystemUi = true)
//@Composable
//fun PreviewLoginScreen(){
//    HotTeaTheme {
//        ConversationItem(navigate = {}, username = "", lastMessage = "" id = "")
//    }
//}