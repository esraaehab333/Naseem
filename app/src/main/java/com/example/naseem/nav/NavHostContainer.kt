package com.example.naseem.nav

import FavoriteScreen
import Next7DaysScreen
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.naseem.presentation.alert.view.AddWeatherAlertScreen
import com.example.naseem.presentation.alert.view.WeatherAlertScreen
import com.example.naseem.presentation.alert.viewModel.WeatherAlertViewModel
import com.example.naseem.presentation.settings.view.SettingsScreen
import com.example.naseem.presentation.fav.view.AddFavoritePlaceScreen
import com.example.naseem.presentation.fav.viewModels.FavoriteViewModel
import com.example.naseem.presentation.home.view.HomeScreen
import com.example.naseem.presentation.home.viewModels.HomeViewModel
import com.example.naseem.presentation.settings.viewModel.SettingsViewModel
import com.example.naseem.utils.Routes

@Composable
fun NavHostContainer(
    navController: NavHostController,
    padding: PaddingValues,
    color: Color,
    image: Int,
    settingsViewModel:SettingsViewModel,
    viewModel: HomeViewModel,
    favoriteViewModel: FavoriteViewModel,
    alertViewModel: WeatherAlertViewModel,
    onLanguageChange: (String) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Routes.HOME,
        modifier = Modifier.padding(paddingValues = padding),
        builder = {
            composable(Routes.FAVDETAILSHOME) { backStackEntry ->
                val lat = backStackEntry.arguments?.getString("lat")?.toDoubleOrNull()
                val lon = backStackEntry.arguments?.getString("lon")?.toDoubleOrNull()

                HomeScreen(
                    settingsViewModel = settingsViewModel,
                    color = color,
                    viewModel = viewModel,
                    image = image,
                    onNext7DaysClick = {
                        navController.navigate(Routes.NEXT7DAYS)
                    },
                    lat = lat,
                    lon = lon
                )
            }
            composable(Routes.HOME) {
                HomeScreen(
                    color = color,
                    viewModel = viewModel,
                    image = image,
                    settingsViewModel = settingsViewModel,
                    onNext7DaysClick = {
                        navController.navigate(Routes.NEXT7DAYS)
                    }
                )
            }
            composable(Routes.ALERT) {
                WeatherAlertScreen(
                    color=color,
                    viewModel=alertViewModel,
                    onFloatingActionButtonClicked = {
                        navController.navigate(Routes.ADDWEATHERALERT)
                    }
                )
            }
            composable(Routes.FAVORITE) {
                FavoriteScreen(
                    color = color,
                    viewModel = favoriteViewModel,
                    onFavDetailsClick = { lat, lon ->
                        navController.navigate("favDetailsHome/$lat/$lon")
                    },
                    onFloatingActionButtonClicked = {
                        navController.navigate(Routes.ADDFAVORITEPLACE)
                    }
                )
            }
            composable(Routes.SETTINGS) {

                SettingsScreen(
                    settingsViewModel = settingsViewModel,
                    color = color,
                    onLanguageChange = onLanguageChange
                )
            }
            composable(Routes.NEXT7DAYS) {
                Next7DaysScreen(
                    color = color,
                    viewModel = viewModel,
                    onBackButtonClick = {
                        navController.popBackStack()
                    }
                )
            }
            composable(Routes.ADDWEATHERALERT) {
                AddWeatherAlertScreen(
                    color = color,
                    viewModel=alertViewModel,
                    onBackButtonClick = {
                        navController.popBackStack()
                    }
                )
            }
            composable(Routes.ADDFAVORITEPLACE) {
                AddFavoritePlaceScreen(
                    color = color,
                    viewModel = favoriteViewModel,
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    )
}