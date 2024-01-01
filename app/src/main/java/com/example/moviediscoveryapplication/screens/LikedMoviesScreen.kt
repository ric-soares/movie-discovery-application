package com.example.moviediscoveryapplication.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.moviediscoveryapplication.BottomBarScreen
import com.example.moviediscoveryapplication.R
import com.example.moviediscoveryapplication.components.TopBarComponent

@Composable
fun LikedMoviesScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopBarComponent(
                title = "Liked Movies"
            ) {
                navController.navigate(BottomBarScreen.Home.route) {
                    popUpTo(BottomBarScreen.Home.route) {
                        saveState = true
                    }
                    launchSingleTop = true
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 24.dp),
            ) {
                items(10) {
                    LikedMovieItem()
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun LikedMovieItem() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_profile),
                contentDescription = null,
                modifier = Modifier
                    .weight(0.8f)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1.2f)
            ) {
                Text(
                    text = "Genre",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp)
                )
                Text(
                    text = "Title",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp)
                )
                Text(
                    text = "Movie",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}