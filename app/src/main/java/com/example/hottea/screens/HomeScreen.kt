package com.example.hottea.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.hottea.R
import com.example.hottea.ViewModels.ConversationsViewModel
import com.example.hottea.ViewModels.UserViewModel
import com.example.hottea.composables.ConversationItem
import com.example.hottea.composables.Input
import com.example.hottea.composables.PrimaryButton
import com.example.hottea.composables.ProfileHeader
import com.example.hottea.models.AuthViewModel
import com.example.hottea.models.TopNavItem
import com.example.hottea.repositories.AuthRepository
import com.example.hottea.repositories.FirestoreRepository
import com.example.hottea.ui.theme.Blue
import com.example.hottea.ui.theme.PrimaryLight
import com.example.hottea.ui.theme.Red
import com.example.hottea.ui.theme.gradient
import com.google.accompanist.pager.ExperimentalPagerApi


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalFoundationApi::class, ExperimentalPagerApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel,
    repository: AuthRepository = AuthRepository(),
    navToProfile: () -> Unit,
    navController: NavHostController = rememberNavController(),
    navToConversation: (chatId: String) -> Unit,
    viewModel: UserViewModel = viewModel(),
    firestoreRepository: FirestoreRepository = FirestoreRepository(),
    conversationsViewModel: ConversationsViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    navToLogin: () -> Unit
){
    val user = remember (viewModel.profile){
        derivedStateOf {viewModel.profile }
    }

    var remove by remember { mutableStateOf(false) }

    val searchText = remember {
        mutableStateOf("")
    }

    val searchBool = remember {
        mutableStateOf(false)
    }

    fun changeValue (){
        searchBool.value = !searchBool.value
    }

    fun logoutUser(){
        repository.signOutUser()
        navToLogin.invoke()
    }

    var status = user.value?.status.toString()
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val backStackEntry = navController.currentBackStackEntryAsState()
    val conversations = conversationsViewModel.conversations
    Log.d("CONVERSATIONS2", conversations.toString())


    val TopNavItems = listOf(
        TopNavItem(
            name = "Conversations",
            route = "Conversations",
            icon = Icons.Default.Home
        ),
        TopNavItem(
            name = "Friends",
            route = "Friends",
            icon = Icons.Default.Person
        )
    )
    Column(modifier = Modifier
        .fillMaxSize()
        .background(gradient)) {
        Column {
            ProfileHeader(status =if(user.value?.status == null)  "No status provided" else user.value?.status.toString() , name = user.value?.username.toString() , image = user.value?.profileImage.toString())
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 0.dp, 12.dp, 0.dp), horizontalArrangement = Arrangement.End) {
                PrimaryButton(color = Blue, icon = Icons.Default.Person , text = "Profile" , onClick = {navToProfile.invoke()})
                Spacer(modifier = Modifier.size(12.dp))
                PrimaryButton(color = Red, icon = Icons.Default.ExitToApp , text = "LogOut" ,onClick = { logoutUser() } )
            }
        }
        Spacer(modifier = Modifier.size(12.dp))

        Column(modifier = Modifier
            .fillMaxSize()
            .clip(shape = RoundedCornerShape(20.dp))
            .shadow(elevation = 10.dp)
            .background(PrimaryLight, shape = RoundedCornerShape(30.dp, 30.dp, 0.dp, 0.dp))){
            Scaffold(
                topBar = {
                    androidx.compose.material3.NavigationBar(
                        containerColor = PrimaryLight,
                        modifier = Modifier.height(75.dp)
                    ) {
                        TopNavItems.forEach { item ->
                            val selected = item.route == backStackEntry.value?.destination?.route

                            NavigationBarItem(
                                selected = selected,
                                onClick = { navController.navigate(item.route) },
                                label = {
                                    Text(
                                        text = item.name,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.White
                                    )
                                },
                                icon = {
                                    Icon(
                                        imageVector = item.icon,
                                        contentDescription = "${item.name} Icon",
                                        tint = if (selected) Blue else Color.Gray
                                    )
                                }
                            )
                        }
                    }
                },
                content = {
                        NavHost(
                            navController = navController,
                            startDestination = TopNavItems.first().route.toString(),
                            Modifier.background(color = PrimaryLight)
                        ) {
                            composable(route = TopNavItems.first().route) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .shadow(elevation = 10.dp)
                                        .background(color = PrimaryLight)
                                ) {
                                    Spacer(modifier = Modifier.size(83.dp))
                                    LazyColumn(
                                        contentPadding = PaddingValues(0.dp),
                                        verticalArrangement = Arrangement.spacedBy(0.dp),
                                    ) {
                                        items(conversations) { convo ->
                                            val userOneName = convo!!.userOne.username ?: "None"
                                            val userTwoName = convo!!.userTwo.username ?: "None"
                                            val lastMessage = convo!!.lastMessage ?: "no Messages..."
                                            val chatId = convo!!.id ?: "nothing"
                                            val displayName = if( user.value?.username.toString() ==  convo!!.userOne.username){
                                                convo!!.userTwo.username
                                            } else{
                                                convo!!.userOne.username
                                            }
                                            Log.d("USERNAMES", "${ userOneName}, ${user.value?.username.toString()}")
                                            ConversationItem(username = displayName, lastMessage = if(lastMessage == "") "No messages..." else lastMessage, navigate = {navToConversation(chatId)}, id = chatId)
                                        }
                                    }
                                }
                            }

                            composable(route = TopNavItems.last().route) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()

                                ) {
                                    Spacer(modifier = Modifier.size(63.dp))

                                    if(searchBool.value){
                                        TextButton(onClick = { changeValue()}) {
                                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                                            Text(text = "Back")
                                        }
                                    }

                                   Row(
                                       Modifier
                                           .fillMaxWidth()
                                           .padding(12.dp), Arrangement.SpaceBetween, Alignment.CenterVertically) {

                                       if (!searchBool.value) {
                                           PrimaryButton(
                                               color = Blue,
                                               icon = ImageVector.vectorResource(id = R.drawable.ic_add),
                                               text = "Add Friend"
                                           ) {
                                             changeValue()
                                           }

                                           PrimaryButton(
                                               color = Red,
                                               icon = ImageVector.vectorResource(id = if(remove) R.drawable.ic_cancel else R.drawable.ic_remove),
                                               text = if(remove) "Cancel" else "Remove Friend"
                                           ) {
                                               remove = !remove
                                           }
                                       } else {
                                           Column(Modifier.width(250.dp)) {
                                               Input(
                                                   textValue = searchText.value,
                                                   changedVal = { newText -> searchText.value = newText },
                                                   label = "Search people",
                                                   icon = Icons.Default.Search,
                                                   keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                                                   transformation = VisualTransformation.None
                                               )
                                           }


                                           PrimaryButton(color = Blue, icon = ImageVector.vectorResource(
                                               id = R.drawable.ic_add
                                           ), text = "Add he" ) {
                                                firestoreRepository.addFriend(searchText.value , user.value?.id.toString())
//                                               Log.d("FRIEND", "HELL Yea Brother")
                                           }
                                       }


                                   }

                                    FriendsList(uid =user.value!!.id, navToConversation = navToConversation, remove = remove)
                                }
                            }
                        }
                }
            )
        }
    }
}



//@Preview(showSystemUi = true)
//@Composable
//fun PreviewHome(){
//    HotTeaTheme {
//        HomeScreen(authViewModel = AuthViewModel(), navToProfile = {}, navToConversation = {})
//    }
//}