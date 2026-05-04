package com.example.naseem.presentation.home.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.naseem.common.ApiState
import com.example.naseem.data.repository.SettingsRepository
import com.example.naseem.data.repository.WeatherRepository
import com.example.naseem.data.models.dto.ForecastResponse
import com.example.naseem.data.models.dto.WeatherResponse
import com.example.naseem.utils.LocationHelper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val weatherRepository: WeatherRepository,
    private val locationHelper: LocationHelper,
    private val settingsRepository: SettingsRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _weatherData = MutableStateFlow<WeatherResponse?>(null)
    val weatherData: StateFlow<WeatherResponse?> = _weatherData

    private val _forecastData = MutableStateFlow<ForecastResponse?>(null)
    val forecastData: StateFlow<ForecastResponse?> = _forecastData

    private val _errorMessage = MutableSharedFlow<String>()
    val errorMessage: SharedFlow<String> = _errorMessage

    fun getCurrentWeather(lat: Double, lon: Double) {
        viewModelScope.launch(dispatcher) {
            _isLoading.value = true
            when (val result = weatherRepository.getCurrentWeather(lat, lon)) {
                is ApiState.Success -> _weatherData.value = result.data
                is ApiState.Failure -> _errorMessage.emit(result.msg.message ?: "Unknown Error occurred")
                else -> {}
            }
            _isLoading.value = false
        }
    }

    fun getFiveDayForecast(lat: Double, lon: Double) {
        viewModelScope.launch(dispatcher) {
            _isLoading.value = true
            when (val result = weatherRepository.getFiveDayForecast(lat, lon)) {
                is ApiState.Success -> _forecastData.value = result.data
                is ApiState.Failure -> _errorMessage.emit(result.msg.message ?: "Unknown Error")
                else -> {}
            }
            _isLoading.value = false
        }
    }

    /**
     * Entry point called from the Home screen on launch / refresh.
     *
     * Logic:
     *  • useGps == true  → ask the OS for the live device location (existing behaviour)
     *  • useGps == false → use the lat/lon the user pinned on the Settings map.
     *                      If they haven't pinned anything yet, fall back to GPS anyway
     *                      so the screen never stays blank.
     */
    fun getLocationAndFetchWeather() {
        val useGps = settingsRepository.useGps.value

        if (useGps) {
            // ── GPS path (unchanged behaviour) ────────────────────────────
            _isLoading.value = true
            locationHelper.getCurrentLocation { lat, lon ->
                fetchWeatherForCoords(lat, lon)
            }
        } else {
            // ── Saved-map-location path ────────────────────────────────────
            val lat = settingsRepository.savedLat.value
            val lon = settingsRepository.savedLon.value

            if (lat != null && lon != null) {
                fetchWeatherForCoords(lat, lon)
            } else {
                // No map location saved yet → fall back to GPS silently
                _isLoading.value = true
                locationHelper.getCurrentLocation { gpsLat, gpsLon ->
                    fetchWeatherForCoords(gpsLat, gpsLon)
                }
            }
        }
    }

    /** Fetches both current weather and 5-day forecast for the given coords. */
    private fun fetchWeatherForCoords(lat: Double, lon: Double) {
        viewModelScope.launch(dispatcher) {
            _isLoading.value = true
            val weatherResult  = weatherRepository.getCurrentWeather(lat, lon)
            val forecastResult = weatherRepository.getFiveDayForecast(lat, lon)
            if (weatherResult  is ApiState.Success) _weatherData.value  = weatherResult.data
            if (forecastResult is ApiState.Success) _forecastData.value = forecastResult.data
            _isLoading.value = false
        }
    }
}

class HomeViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(
            weatherRepository  = WeatherRepository(context.applicationContext),
            locationHelper     = LocationHelper(context.applicationContext),
            settingsRepository = SettingsRepository.getInstance(context.applicationContext)
        ) as T
    }
}