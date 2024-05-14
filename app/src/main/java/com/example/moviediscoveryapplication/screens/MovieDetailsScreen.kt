package com.example.moviediscoveryapplication.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.moviediscoveryapplication.R
import com.example.moviediscoveryapplication.components.TopBarComponent
import com.example.moviediscoveryapplication.ui.theme.DarkColorScheme

@Composable
fun MovieDetailsScreen() {

    Scaffold(
        topBar = {
            TopBarComponent(
                title = "Movie name"
            ) {

            }
        },
        content = { paddingValues ->
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {

                Box(
                    modifier = Modifier.weight(0.6F)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(DarkColorScheme.primary.copy(alpha = 0.85f))
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        DarkColorScheme.primary.copy(alpha = 0.96f)
                                    )
                                )
                            )
                    )

                }

                Column(
                    modifier = Modifier
                        .weight(0.4F)
                        .background(DarkColorScheme.primary)
                        .fillMaxSize()
                ) {

                }

            }
        }
    )
}

@Preview
@Composable
fun MovieDetailsScreenPreview() {
    MovieDetailsScreen()
}