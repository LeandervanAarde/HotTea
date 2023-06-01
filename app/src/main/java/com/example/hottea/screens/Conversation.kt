package com.example.hottea.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hottea.R
import com.example.hottea.composables.IncomingMessage
import com.example.hottea.composables.OutGoingMessage
import com.example.hottea.composables.PrimaryButton
import com.example.hottea.ui.theme.Blue
import com.example.hottea.ui.theme.Primary
import com.example.hottea.ui.theme.PrimaryLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(modifier: Modifier = Modifier){
    var message by remember {
        mutableStateOf("")
    }

    Column(
        modifier
            .fillMaxSize()
            .background(color = PrimaryLight)
    ) {
        Row(
            modifier
                .height(130.dp)
                .fillMaxWidth()

                .background(color = Primary, shape = RoundedCornerShape(0.dp, 0.dp, 32.dp, 32.dp))
                .padding(12.dp)) {
            Image(painter = painterResource(id = R.drawable.tester), contentDescription = null,
                modifier
                    .clip(
                        RoundedCornerShape(14.dp)
                    )
                    .width(100.dp)
                    .aspectRatio(1F))
            Spacer(modifier = Modifier.size(15.dp))

            Column(
                modifier
                    .fillMaxSize(),
                    verticalArrangement = Arrangement.Center) {
                Text(text = "Leander van Aarde",   color = Color.White, fontSize= 24.sp, fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.size(7.dp))
                Row(modifier = Modifier.padding(0.dp, 0.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier
                        .size(10.dp)
                        .clip(shape = CircleShape)
                        .background(Color.Green)){

                    }
                    Spacer(modifier = Modifier.size(18.dp))
                    Text(text = "Available",   color = Color.Green, fontSize= 12.sp)

                }
                Spacer(modifier = Modifier.size(7.dp))
                Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                    Image(imageVector = Icons.Default.Edit , contentDescription = null, modifier = Modifier.size(25.dp), colorFilter = ColorFilter.tint(Color.White) )
                    Spacer(modifier = Modifier.size(12.dp))
                    Text(text = "I just like to eat cheese and stuff like...",   color = Color.Gray, fontSize= 12.sp)
                }
            }
        }

        Spacer(modifier = modifier.size(5.dp))
        TextButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            Text(text = "Back")
        }
        
        Column(modifier.fillMaxSize().padding(0.dp, 0.dp, 0.dp, 12.dp)) {

            Column(
                Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
                    .weight(1F)) {
                OutGoingMessage(text = "This is an example message from me as a user, I think this could be pretty nice to talk to someone , the blue colour is also pretty nice and I happen to like it enough")
                IncomingMessage(text = "This is an example message from me as a user, I think this could be pretty")

            }

            Row(modifier.padding(12.dp, 0.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                OutlinedTextField(
                    modifier = Modifier.width(266.dp),
                    textStyle = TextStyle(color = Color.White),
                    value = message ,
                    onValueChange = { message = it },
//                visualTransformation = transformation
                )

                Spacer(modifier.size(15.dp))

                PrimaryButton(color = Blue, icon = ImageVector.vectorResource(id = R.drawable.ic_send), text = "Send") {

                }

            }
            
        }

    }
}