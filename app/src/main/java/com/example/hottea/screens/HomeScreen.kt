package com.example.hottea.screens

import android.R.color
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.hottea.composables.ConverSationItem
import com.example.hottea.composables.PrimaryButton
import com.example.hottea.composables.ProfileHeader
import com.example.hottea.composables.tabRowItems
import com.example.hottea.models.AuthViewModel
import com.example.hottea.repositories.AuthRepository
import com.example.hottea.ui.theme.Blue
import com.example.hottea.ui.theme.HotTeaTheme
import com.example.hottea.ui.theme.Primary
import com.example.hottea.ui.theme.PrimaryLight
import com.example.hottea.ui.theme.Red
import com.example.hottea.ui.theme.gradient
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class, ExperimentalPagerApi::class)
@Composable

fun HomeScreen(modifier: Modifier = Modifier, authViewModel: AuthViewModel, repository: AuthRepository = AuthRepository()){

    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()


    Column(modifier = Modifier
        .fillMaxSize()
        .background(gradient)) {
        Column {
            ProfileHeader()
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 0.dp, 12.dp, 0.dp), horizontalArrangement = Arrangement.End) {
                PrimaryButton(color = Blue, icon = Icons.Default.Person , text = "Profile" , onClick = { Log.i("ehye", "null")})
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


            Column(modifier = Modifier
                .fillMaxSize()
                .shadow(elevation = 10.dp)
                .verticalScroll(
                    rememberScrollState()
                )) {
                ConverSationItem()
                ConverSationItem()
                ConverSationItem()
                ConverSationItem()
                ConverSationItem()
                ConverSationItem()
                ConverSationItem()
                ConverSationItem()
                ConverSationItem()

                ConverSationItem()
                ConverSationItem()
                ConverSationItem()
                ConverSationItem()
                ConverSationItem()
                ConverSationItem()
                ConverSationItem()
            }
        }
    }
}


//@Preview(showSystemUi = true)
//@Composable
//fun PreviewHome(){
//    HotTeaTheme {
//        HomeScreen(navController = NavController)
//    }
//}