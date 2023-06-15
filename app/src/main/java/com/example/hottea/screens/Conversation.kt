package com.example.hottea.screens

import android.util.Log
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.hottea.R
import com.example.hottea.ViewModels.ChatViewModel
import com.example.hottea.ViewModels.ConversationsViewModel
import com.example.hottea.composables.IncomingMessage
import com.example.hottea.composables.OutGoingMessage
import com.example.hottea.composables.PrimaryButton
import com.example.hottea.models.Conversation
import com.example.hottea.models.Message
import com.example.hottea.ui.theme.Blue
import com.example.hottea.ui.theme.Primary
import com.example.hottea.ui.theme.PrimaryLight
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(modifier: Modifier = Modifier, navBack: () -> Unit, chatId: String?,
               chatViewModel: ChatViewModel = viewModel(), conversationsViewModel: ConversationsViewModel = androidx.lifecycle.viewmodel.compose.viewModel()){
    var message by remember {
        mutableStateOf("")
    }
    val currentUser = conversationsViewModel.userId
    val isChatNotBlank = chatId.isNullOrBlank()
    val messages = chatViewModel.messageList ?: listOf<Message>()
    val conversations = conversationsViewModel.conversations
    val filteredConversation = conversations?.filter { convo ->
        convo?.id == chatId
    }

    val firstConversation = filteredConversation?.firstOrNull()
    val otherUser = if (firstConversation?.userOne?.id == currentUser) {
        firstConversation?.userTwo
    } else {
        firstConversation?.userOne
    }
    val currUserOb = if (firstConversation?.userOne?.id != currentUser) {
        firstConversation?.userTwo
    } else {
        firstConversation?.userOne
    }

     fun readableDate(date: Date): String {
        val dateFormat = SimpleDateFormat("MMMM dd - hh:mm a", Locale.getDefault())
        return dateFormat.format(date)
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
            AsyncImage(model = otherUser?.profileImage, contentDescription = null,
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
                Text( text = otherUser?.username.toString(),   color = Color.White, fontSize= 24.sp, fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.size(7.dp))
                Row(modifier = Modifier.padding(0.dp, 0.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier
                        .size(10.dp)
                        .clip(shape = CircleShape)
                        .background(if (otherUser?.available == true) Color.Green else Color.Red)){
                    }
                    Spacer(modifier = Modifier.size(18.dp))
                    Text(text = if(otherUser?.available == true) "Available" else "Offline",   color = (if (otherUser?.available == true) Color.Green else Color.Red), fontSize= 12.sp)

                }
                Spacer(modifier = Modifier.size(7.dp))
                Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                    Image(imageVector = Icons.Default.Edit , contentDescription = null, modifier = Modifier.size(25.dp), colorFilter = ColorFilter.tint(Color.White) )
                    Spacer(modifier = Modifier.size(12.dp))
                    Text(text = otherUser?.status.toString(),   color = Color.Gray, fontSize= 12.sp)
                }
            }
        }

        Spacer(modifier = modifier.size(5.dp))
        TextButton(onClick = { navBack.invoke() }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            Text(text = "Back")
        }
        
        Column(
            modifier
                .fillMaxSize()
                .padding(0.dp, 0.dp, 0.dp, 12.dp)) {

            LazyColumn(
                modifier
                    .weight(1F)
                    .fillMaxSize(), reverseLayout = true){

                items(messages){ message ->
                   if(chatViewModel.userId == message.userId){

                       OutGoingMessage(text = message.message, image = currUserOb?.profileImage.toString(), date = message.time)
                   } else{
                       IncomingMessage(text = message.message, image = otherUser?.profileImage.toString(), date = message.time)
                   }
                }
            }

            Row(modifier.padding(12.dp, 0.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                OutlinedTextField(
                    modifier = Modifier.width(260.dp),
                    textStyle = TextStyle(color = Color.White),
                    value = message ,
                    onValueChange = { message = it },
//                visualTransformation = transformation
                )
                Spacer(modifier.size(15.dp))

                PrimaryButton(color = Blue, icon = ImageVector.vectorResource(id = R.drawable.ic_send), text = "Send") {
                            chatViewModel.sendNewMessage(message, chatId ?: "")
                    message = ""
                }
            }
        }
    }
    LaunchedEffect(key1 = Unit){
        if(!isChatNotBlank){
            chatViewModel.getRealtimeMessages(chatId ?: "")
            Log.d("CHAT", "ATTEMPT")
        } else{
            Log.d("CHAT", "ATTEMPT ERR")
        }
    }
}
