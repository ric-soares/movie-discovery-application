package com.example.moviediscoveryapplication

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.moviediscoveryapplication.screens.HomeScreen
import com.example.moviediscoveryapplication.screens.LikedMoviesScreen
import com.example.moviediscoveryapplication.screens.ProfileScreen
import com.example.moviediscoveryapplication.viewmodel.HomeScreenViewModel

@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route) { navBackStackEntry ->
            val viewModel = hiltViewModel<HomeScreenViewModel>()
            HomeScreen(viewModel)
        }
        composable(route = BottomBarScreen.LikedMovies.route) {
            LikedMoviesScreen()
        }
        composable(route = BottomBarScreen.Profile.route) {
            ProfileScreen()
        }
    }
}