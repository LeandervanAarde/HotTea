package com.example.hottea.composables

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.hottea.R
import com.example.hottea.repositories.FirestoreRepository
import com.example.hottea.ui.theme.Blue
import com.example.hottea.ui.theme.Primary
import com.example.hottea.ui.theme.Red

@Composable
fun FriendCard(modifier: Modifier = Modifier, name: String, image: String, available: Boolean, uid: String, friendUid: String, firestoreRepository: FirestoreRepository = FirestoreRepository(), navToConversation: (chatId: String) -> Unit, remove: Boolean){
    Column(
        Modifier
            .background(color = Color.Transparent, shape = RoundedCornerShape(12.dp))
            .fillMaxWidth()
            .padding(0.dp, 0.dp, 0.dp, 12.dp)
            ){

        AsyncImage(model = image, contentDescription = null,
            modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(color = Primary, shape = RoundedCornerShape(12.dp))
                .clip(
                    RoundedCornerShape(12.dp)
                ),
            contentScale = ContentScale.FillBounds
        )

        Column(modifier.padding(12.dp, 0.dp)) {
            Text(text = name, color = Color.White, fontSize= 18.sp, fontWeight = FontWeight.Medium, modifier = Modifier.padding(0.dp, 2.5.dp))

            Row(modifier = Modifier.padding(0.dp, 2.5.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier
                    .size(10.dp)
                    .clip(shape = CircleShape)
                    .background(if(available) Color.Green else Color.Red)){

                }
                Spacer(modifier = Modifier.size(18.dp))
                Text(text = if (available) "Available" else "Offline",   color = if(available) Color.Green else Color.Red, fontSize= 12.sp)
            }

            Row(
                modifier
                    .fillMaxWidth()
                    .padding(0.dp, 3.dp), horizontalArrangement = Arrangement.Center) {
                if(remove){
                    PrimaryButton(color = Red, icon = ImageVector.vectorResource(id = R.drawable.ic_remove), text = "Unfriend" ) {
                        Log.i("USERONETWO", friendUid)
                    }

                }else{
                    PrimaryButton(color = Blue, icon = ImageVector.vectorResource(id = R.drawable.ic_chat), text = "Let's chat" ) {

                        firestoreRepository.createNewConversation(uid, friendUid)
                        { chatId ->
                            navToConversation(chatId)
                        }
                        Log.i("USERONE", uid)
                        Log.i("USERONETWO", friendUid)
                    }
                }
            }
        }
    }
}
