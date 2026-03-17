package com.example.naseem.presentation.home.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.naseem.R
import com.example.naseem.presentation.home.componets.TodayForecastWeather
import com.example.naseem.presentation.home.componets.WeatherDetailsCard
import com.example.naseem.presentation.home.componets.WeatherHeaderSection
import com.example.naseem.presentation.home.viewModels.HomeViewModel
import com.example.naseem.presentation.settings.viewModel.SettingsViewModel
import com.example.naseem.ui.theme.White100
import com.example.naseem.utils.convertTemp
import com.example.naseem.utils.convertWind
import com.example.naseem.utils.formatUnixTimestamp

@Composable
fun HomeScreen(
    color: Color,
    image: Int,
    viewModel: HomeViewModel,
    onNext7DaysClick: () -> Unit,
    lon: Double? = null,
    lat: Double? = null,
    settingsViewModel: SettingsViewModel,
) {
    val weatherData by viewModel.weatherData.collectAsState()
    val forecastData by viewModel.forecastData.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val tempUnit by settingsViewModel.temperatureUnit.collectAsState()
    val windUnit by settingsViewModel.windSpeedUnit.collectAsState()

    LaunchedEffect(lat, lon) {
        if (lat != null && lon != null) {
            viewModel.getCurrentWeather(lat, lon)
            viewModel.getFiveDayForecast(lat, lon)
        } else {
            viewModel.getLocationAndFetchWeather()
        }
    }

    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(White100),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = color)
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = White100)
        ) {
            Box(modifier = Modifier.background(White100)) {
                WeatherHeaderSection(
                    address = weatherData?.name ?: stringResource(R.string.unknown_location),
                    date = weatherData?.dt?.let { formatUnixTimestamp(it) }
                        ?: stringResource(R.string.unknown_date),
                    image = image,
                    tempDegree = weatherData?.main?.temp?.let { convertTemp(it, tempUnit) } ?: "0°C"
                )
                WeatherDetailsCard(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .offset(y = 50.dp),
                    color = color,
                    humidity = "${weatherData?.main?.humidity}%",
                    windSpeed = weatherData?.wind?.speed?.let { convertWind(it, windUnit) } ?: "0 m/s",
                    pressure = "${weatherData?.main?.pressure} ${stringResource(R.string.hpa)}",
                    clouds = "${weatherData?.clouds?.all}%"
                )
            }
            Spacer(modifier = Modifier.height(80.dp))
            TodayForecastWeather(
                color = color,
                forecastData = forecastData,
                onNext7DaysClick = onNext7DaysClick
            )
        }
    }
}