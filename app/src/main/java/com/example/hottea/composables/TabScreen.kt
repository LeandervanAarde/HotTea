package com.example.hottea.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.hottea.models.TabRowItem

val tabRowItems = listOf(
    TabRowItem(
        title = "Tab 1",
        screen = { TabScreen(text = "Tab 1") },
        icon = Icons.Rounded.Place,
    ),
    TabRowItem(
        title = "Tab 2",
        screen = { TabScreen(text = "Tab 2") },
        icon = Icons.Rounded.Search,
    ),
    TabRowItem(
        title = "Tab 3",
        screen = { TabScreen(text = "Tab 3") },
        icon = Icons.Rounded.Star,
    )
)



@Composable
fun TabScreen(text: String){
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(100.dp), contentAlignment = Alignment.Center){
        Text(text = text, style = MaterialTheme.typography.bodyMedium)
    }
}