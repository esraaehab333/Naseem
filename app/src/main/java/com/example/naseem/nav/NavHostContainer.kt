package com.example.naseem.nav

import Next7DaysScreen
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.naseem.presentation.fav.view.FavoriteScreen
import com.example.naseem.presentation.settings.view.SettingsScreen
import com.example.naseem.presentation.alert.view.AlertScreen
import com.example.naseem.presentation.home.view.HomeScreen
import com.example.naseem.presentation.home.viewModels.HomeViewModel
import com.example.naseem.utils.Routes

@Composable
fun NavHostContainer(
    navController: NavHostController,
    padding: PaddingValues,
    color: Color,
    image:Int,
    viewModel: HomeViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Routes.HOME,
        modifier = Modifier.padding(paddingValues = padding),
        builder = {
            composable(Routes.HOME) {
                HomeScreen(
                    color = color,
                    viewModel = viewModel,
                    image = image,
                    onNext7DaysClick = {
                        navController.navigate(Routes.NEXT7DAYS)
                    })
            }
            composable(Routes.ALERT) {
                AlertScreen()
            }
            composable(Routes.FAVORITE) {
                FavoriteScreen()
            }
            composable(Routes.SETTINGS) {
                SettingsScreen(color=color)
            }
            composable(Routes.NEXT7DAYS) {
                Next7DaysScreen(color=color,
                    onBackButtonClick = {
                        navController.popBackStack()
                    })
            }
        })
}
