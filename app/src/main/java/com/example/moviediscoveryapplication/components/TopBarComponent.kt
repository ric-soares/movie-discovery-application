package com.example.moviediscoveryapplication.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarComponent(
    title: String? = null,
    onBackPressed: () -> Unit
) {
    TopAppBar(
        colors = TopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            actionIconContentColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
            navigationIconContentColor = MaterialTheme.colorScheme.background,
            scrolledContainerColor = MaterialTheme.colorScheme.background
        ),
        title = {
            title?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 28.dp),
                    softWrap = true
                )
            }
        },
        modifier = Modifier.fillMaxWidth(),
        navigationIcon = {
            Box(
                modifier = Modifier.padding(8.dp)
            ) {
                IconButton(
                    onClick = { onBackPressed() },
                ) {
                    Icon(
                        modifier = Modifier.padding(10.dp),
                        imageVector = Icons.Filled.ArrowBackIosNew,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            }
        }
    )
}