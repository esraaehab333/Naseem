package com.example.naseem

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
import androidx.navigation.compose.rememberNavController
import com.example.naseem.nav.NavHostContainer
import com.example.naseem.presentation.home.componets.BottomNavigationBar
import com.example.naseem.presentation.home.viewModels.HomeViewModel
import com.example.naseem.presentation.home.viewModels.HomeViewModelFactory
import com.example.naseem.ui.theme.MildPrimary
import com.example.naseem.ui.theme.NaseemTheme
import com.example.naseem.utils.getThemeConfig

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val context = LocalContext.current
            val viewModel: HomeViewModel = viewModel(
                factory = HomeViewModelFactory(context)
            )
            val weatherData by viewModel.weatherData.collectAsState()
            val currentTemp = weatherData?.main?.temp ?: 20.0
            val dynamicColor = getThemeConfig(currentTemp)

            NaseemTheme(dynamicColor = false, darkTheme = false) {
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    contentWindowInsets = WindowInsets(0, 0, 0, 0),
                    bottomBar = {
                        BottomNavigationBar(navController = navController, color = dynamicColor.color)
                    }
                ) { padding ->
                    NavHostContainer(
                        navController = navController,
                        padding = padding,
                        color = dynamicColor.color,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}