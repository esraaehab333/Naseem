package com.example.naseem.nav

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.naseem.ExploreScreen
import com.example.naseem.fav.FavoriteScreen
import com.example.naseem.SettingsScreen
import com.example.naseem.home.screens.HomeScreen
import com.example.naseem.utils.Routes

@Composable
fun NavHostContainer(
    navController: NavHostController,
    padding: PaddingValues,
    color: Color
) {
    NavHost(
        navController = navController,
        startDestination = Routes.HOME,
        modifier = Modifier.padding(paddingValues = padding),
        builder = {
            composable(Routes.HOME) {
                HomeScreen(color = color)
            }
            composable(Routes.EXPLORE) {
                ExploreScreen()
            }
            composable(Routes.FAVORITE) {
                FavoriteScreen()
            }
            composable(Routes.SETTINGS) {
                SettingsScreen()
            }
        })
}
