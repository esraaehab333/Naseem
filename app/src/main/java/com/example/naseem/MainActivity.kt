package com.example.naseem

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.naseem.nav.NavHostContainer
import com.example.naseem.presentation.alert.viewModel.WeatherAlertViewModel
import com.example.naseem.presentation.alert.viewModel.WeatherAlertViewModelFactory
import com.example.naseem.presentation.fav.viewModels.FavoriteViewModel
import com.example.naseem.presentation.fav.viewModels.FavoriteViewModelFactory
import com.example.naseem.presentation.home.componets.BottomNavigationBar
import com.example.naseem.presentation.home.viewModels.HomeViewModel
import com.example.naseem.presentation.home.viewModels.HomeViewModelFactory
import com.example.naseem.ui.theme.NaseemTheme
import com.example.naseem.utils.Routes
import com.example.naseem.utils.getThemeConfig
import java.util.Locale

class MainActivity : ComponentActivity() {

    override fun attachBaseContext(newBase: Context) {
        val lang = newBase
            .getSharedPreferences("settings", MODE_PRIVATE)
            .getString("language", "en") ?: "en"
        super.attachBaseContext(applyLocale(newBase, lang))
    }
    private fun applyLocale(context: Context, lang: String): Context {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        return context.createConfigurationContext(config)
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val viewModel: HomeViewModel = viewModel(factory = HomeViewModelFactory(context))
            val favoriteViewModel: FavoriteViewModel = viewModel(factory = FavoriteViewModelFactory(context))
            val alertViewModel:WeatherAlertViewModel = viewModel(factory = WeatherAlertViewModelFactory(context))
            val weatherData by viewModel.weatherData.collectAsState()
            val currentTemp = weatherData?.main?.temp ?: 20.0
            val dynamicColor = getThemeConfig(currentTemp)

            NaseemTheme(dynamicColor = false, darkTheme = false) {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    contentWindowInsets = WindowInsets(0, 0, 0, 0),
                    bottomBar = {
                        if (currentRoute != Routes.NEXT7DAYS && currentRoute != Routes.ADDFAVORITEPLACE) {
                            BottomNavigationBar(
                                navController = navController,
                                color = dynamicColor.color
                            )
                        }
                    }
                ) { padding ->
                    NavHostContainer(
                        navController = navController,
                        padding = padding,
                        color = dynamicColor.color,
                        viewModel = viewModel,
                        favoriteViewModel = favoriteViewModel,
                        alertViewModel = alertViewModel,
                        image = dynamicColor.imageRes,
                        onLanguageChange = { langCode ->
                            getSharedPreferences("settings", MODE_PRIVATE)
                                .edit()
                                .putString("language", langCode)
                                .apply()
                            recreate()
                        }
                    )
                }
            }
        }
    }
}