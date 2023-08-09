package com.example.moviediscoveryapplication

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomBar(navController = navController) },
        content = { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                BottomNavGraph(navController = navController)
            }
        }
    )
}

@Composable
fun BottomBar(
    navController: NavHostController
) {

    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.LikedMovies,
        BottomBarScreen.Profile,
    )

    BottomAppBar(containerColor = MaterialTheme.colorScheme.background) {
        val backStackEntry = navController.currentBackStackEntryAsState()

        screens.forEach { screen ->

            val currentRoute = backStackEntry.value?.destination?.route
            val selected = currentRoute == screen.route

            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = "Navigation Icon",
                        tint = if (selected) Color.Cyan else Color.Gray
                    )
                },
                selected = selected,
                label = {
                    Text(
                        text = if (selected) screen.title else "",
                        color = if (selected) Color.Cyan else Color.Gray
                    )
                },
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route)
                    }
                }
            )
        }
    }
}