package com.example.hottea.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hottea.ViewModels.FriendsViewModel
import com.example.hottea.composables.FriendCard

@Composable
fun FriendsList(modifier: Modifier = Modifier, friendsViewModel: FriendsViewModel = viewModel(), uid: String, navToConversation: (chatId: String) -> Unit, remove: Boolean){
    val friends = friendsViewModel.friends

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(friends.size) { index ->
            val friend = friends[index]
            FriendCard(name = friend?.username ?: "" , image = friend?.profileImage ?: " " , available = friend?.available ?: false, uid = uid , friendUid = friend?.id ?: " ",  navToConversation = navToConversation, remove = remove)
        }
    }
}