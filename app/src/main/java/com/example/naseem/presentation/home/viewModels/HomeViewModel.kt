package com.example.naseem.presentation.home.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.naseem.common.ApiState
import com.example.naseem.data.datasource.WeatherRepository
import com.example.naseem.data.dto.ForecastResponse
import com.example.naseem.data.dto.WeatherResponse
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

    fun getLocationAndFetchWeather() {
        _isLoading.value = true
        locationHelper.getCurrentLocation { lat, lon ->
            viewModelScope.launch(dispatcher) {
                val weatherResult = weatherRepository.getCurrentWeather(lat, lon)
                val forecastResult = weatherRepository.getFiveDayForecast(lat, lon)
                if (weatherResult is ApiState.Success) _weatherData.value = weatherResult.data
                if (forecastResult is ApiState.Success) _forecastData.value = forecastResult.data
                _isLoading.value = false
            }
        }
    }
}

class HomeViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(
            weatherRepository = WeatherRepository(context.applicationContext),
            locationHelper = LocationHelper(context.applicationContext)
        ) as T
    }
}