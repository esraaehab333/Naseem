package com.example.naseem.presentation.home.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.naseem.common.ApiState
import com.example.naseem.data.datasource.WeatherRepository
import com.example.naseem.data.dto.ForecastResponse
import com.example.naseem.data.dto.WeatherResponse
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    context: Context
) : ViewModel() {

    private val weatherRepository = WeatherRepository(context.applicationContext)

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _weatherData = MutableStateFlow<WeatherResponse?>(null)
    val weatherData: StateFlow<WeatherResponse?> = _weatherData

    private val _forecastData = MutableStateFlow<ForecastResponse?>(null)
    val forecastData: StateFlow<ForecastResponse?> = _forecastData

    private val _errorMessage = MutableSharedFlow<String>()
    val errorMessage: SharedFlow<String> = _errorMessage
    
    fun getCurrentWeather(lat: Double, lon: Double) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = weatherRepository.getCurrentWeather(lat, lon)
            when (result) {
                is ApiState.Success -> {
                    _weatherData.value = result.data
                }
                is ApiState.Failure -> {
                    _errorMessage.emit(result.msg.message ?: "Unknown Error occurred")
                }
                else -> {}
            }
            _isLoading.value = false
        }
    }
    fun getFiveDayForecast(lat: Double, lon: Double) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = weatherRepository.getFiveDayForecast(lat, lon)

            when (result) {
                is ApiState.Success -> {
                    _forecastData.value = result.data
                }
                is ApiState.Failure -> {
                    _errorMessage.emit(result.msg.message ?: "Unknown Error")
                }
                else -> {}
            }
            _isLoading.value = false
        }
    }
}
class HomeViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(context.applicationContext) as T
    }
}
