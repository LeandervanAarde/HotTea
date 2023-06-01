package com.example.hottea.screens

import android.R.color
import android.annotation.SuppressLint
import android.security.identity.AccessControlProfile
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.hottea.R
import com.example.hottea.composables.ConverSationItem
import com.example.hottea.composables.PrimaryButton
import com.example.hottea.composables.ProfileHeader
import com.example.hottea.composables.tabRowItems
import com.example.hottea.models.AuthViewModel
import com.example.hottea.models.TopNavItem
import com.example.hottea.repositories.AuthRepository
import com.example.hottea.ui.theme.Blue
import com.example.hottea.ui.theme.HotTeaTheme
import com.example.hottea.ui.theme.Primary
import com.example.hottea.ui.theme.PrimaryLight
import com.example.hottea.ui.theme.Red
import com.example.hottea.ui.theme.gradient
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalFoundationApi::class, ExperimentalPagerApi::class,
    ExperimentalMaterial3Api::class
)
@Composable

fun HomeScreen(modifier: Modifier = Modifier, authViewModel: AuthViewModel, repository: AuthRepository = AuthRepository(), navToProfile: () -> Unit,  navController: NavHostController = rememberNavController(), navToConversation: () -> Unit){

    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val backStackEntry = navController.currentBackStackEntryAsState()
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
            ProfileHeader()
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 0.dp, 12.dp, 0.dp), horizontalArrangement = Arrangement.End) {
                PrimaryButton(color = Blue, icon = Icons.Default.Person , text = "Profile" , onClick = {navToProfile.invoke()})
                Spacer(modifier = Modifier.size(12.dp))
                PrimaryButton(color = Red, icon = Icons.Default.ExitToApp , text = "LogOut" ,onClick = { repository.signOutUser()} )
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
                                    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
                                        ConverSationItem(){
                                            navToConversation.invoke()
                                        }
                                        ConverSationItem(){
//                                            navToConversation.invoke()

                                            Log.i("TEST", "Navigate to conversation")
                                        }

                                        ConverSationItem(){
                                            navToConversation.invoke()
                                        }

                                        ConverSationItem(){
                                            navToConversation.invoke()
                                        }

                                        ConverSationItem(){
                                            navToConversation.invoke()
                                        }
                                    }
                                }
                            }

                            composable(route = TopNavItems.last().route) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()

                                ) {
                                    Spacer(modifier = Modifier.size(83.dp))
                                   Row(
                                       Modifier
                                           .fillMaxWidth()
                                           .padding(12.dp), Arrangement.SpaceBetween) {
                                       PrimaryButton(color = Blue, icon = ImageVector.vectorResource(
                                           id = R.drawable.ic_add
                                       ), text = "Add Friend" ) {
                                           Log.i("Adding friend", "YEs sir")
                                       }

                                       PrimaryButton(color = Red, icon = ImageVector.vectorResource(
                                           id = R.drawable.ic_remove
                                       ), text = "Remove Friend" ) {
                                           Log.i("Adding friend", "YEs sir")
                                       }
                                   }

                                    FriendsList()
                                }
                            }
                        }
                }
            )
        }
    }
}



@Preview(showSystemUi = true)
@Composable
fun PreviewHome(){
    HotTeaTheme {
        HomeScreen(navToProfile = {}, authViewModel = AuthViewModel(), navToConversation = {} )
    }
}