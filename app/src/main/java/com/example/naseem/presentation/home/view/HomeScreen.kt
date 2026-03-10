package com.example.naseem.presentation.home.view

import Next7DaysScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.naseem.R
import com.example.naseem.presentation.home.componets.TodayForecastWeather
import com.example.naseem.presentation.home.componets.WeatherDetailsCard
import com.example.naseem.presentation.home.componets.WeatherHeaderSection
import com.example.naseem.presentation.home.viewModels.HomeViewModel
import com.example.naseem.ui.theme.White100
import com.example.naseem.utils.formatUnixTimestamp

@Composable
fun HomeScreen(
    color: Color,
    image:Int,
    viewModel: HomeViewModel,
    onNext7DaysClick: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.getCurrentWeather(
            lat = 30.0444
            ,lon = 31.2357
            ,apiKey = "17616e217ee841a81fda302a8a2d12b6"
        )
    }
    val weatherData by viewModel.weatherData.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize().background(White100), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = color)
        }
    } else {
        Column(modifier = Modifier.fillMaxSize().background(color = White100)) {
            Box(
                modifier = Modifier.background(White100)
            ) {
                WeatherHeaderSection(
                    address = weatherData?.name ?: "Unknown Location",
                    date = weatherData?.dt?.let { formatUnixTimestamp(it) } ?: "---",
                    image = image,
                    tempDegree = weatherData?.main?.temp?.toInt()?.toString() ?: "0"
                )
                WeatherDetailsCard(
                    modifier = Modifier.align(Alignment.BottomCenter).offset(y = 50.dp),
                    color = color,
                    humidity = "${weatherData?.main?.humidity}%",
                    windSpeed = "${weatherData?.wind?.speed} km/h",
                    pressure = "${weatherData?.main?.pressure} hPa",
                    clouds = "${weatherData?.clouds?.all}%"
                )
            }
            Spacer(modifier = Modifier.height(80.dp))
            TodayForecastWeather(
                color = color,
                onNext7DaysClick = onNext7DaysClick
            )
        }
    }
}