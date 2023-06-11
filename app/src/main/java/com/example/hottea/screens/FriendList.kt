package com.example.hottea.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hottea.ViewModels.FriendsViewModel
import com.example.hottea.composables.FriendCard
import com.example.hottea.ui.theme.Red

@Composable
fun FriendsList(modifier: Modifier = Modifier, friendsViewModel: FriendsViewModel = viewModel(), uid: String){
    val friends = friendsViewModel.friends
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)

    ) {
        items(friends.size) { index ->
            val friend = friends[index]
            FriendCard(name = friend?.username ?: "" , image = friend?.profileImage ?: " " , available = friend?.available ?: false, uid = uid , friendUid = friend?.id ?: " ")
        }
    }
}