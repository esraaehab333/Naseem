package com.example.naseem.presentation.fav.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.naseem.common.ApiState
import com.example.naseem.data.repository.WeatherRepository
import com.example.naseem.data.models.dto.ForecastResponse
import com.example.naseem.data.models.dto.WeatherResponse
import com.example.naseem.data.models.responses.FavoriteModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val repository: WeatherRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _favoritesState = MutableStateFlow<ApiState<List<FavoriteModel>>>(ApiState.Loading)
    val favoritesState: StateFlow<ApiState<List<FavoriteModel>>> = _favoritesState.asStateFlow()

    private val _deleteEvent = MutableSharedFlow<String>()
    val deleteEvent: SharedFlow<String> = _deleteEvent.asSharedFlow()

    private val _addEvent = MutableSharedFlow<String>()
    val addEvent: SharedFlow<String> = _addEvent.asSharedFlow()

    private val _selectedWeather = MutableStateFlow<WeatherResponse?>(null)
    val selectedWeather: StateFlow<WeatherResponse?> = _selectedWeather.asStateFlow()

    private val _selectedForecast = MutableStateFlow<ForecastResponse?>(null)
    val selectedForecast: StateFlow<ForecastResponse?> = _selectedForecast.asStateFlow()

    init {
        getAllFavorites()
    }

    fun getWeatherForFavoriteLocation(lat: Double, lon: Double) {
        viewModelScope.launch {
            val weatherResult = repository.getCurrentWeather(lat, lon)
            val forecastResult = repository.getFiveDayForecast(lat, lon)
            if (weatherResult is ApiState.Success) _selectedWeather.value = weatherResult.data
            if (forecastResult is ApiState.Success) _selectedForecast.value = forecastResult.data
        }
    }

    private fun getAllFavorites() {
        viewModelScope.launch(ioDispatcher) {
            repository.getAllFavWeather()
                .catch { e -> _favoritesState.value = ApiState.Failure(e) }
                .map { list -> list.map { FavoriteModel(it) } }
                .collect { models -> _favoritesState.value = ApiState.Success(models) }
        }
    }

    fun addToFavorites(favorite: FavoriteModel) {
        viewModelScope.launch(ioDispatcher) {
            try {
                repository.insertWeatherToFav(favorite.toEntity())
                _addEvent.emit("${favorite.cityName} added to favorites")
            } catch (e: Exception) {
                _addEvent.emit("Something went wrong")
            }
        }
    }

    fun deleteFromFavorites(favorite: FavoriteModel) {
        viewModelScope.launch(ioDispatcher) {
            try {
                repository.deleteWeatherFromFav(favorite.toEntity())
                _deleteEvent.emit("${favorite.cityName} deleted from favorites")
            } catch (e: Exception) {
                _deleteEvent.emit("Something went wrong")
            }
        }
    }
}

class FavoriteViewModelFactory(
    private val repository: WeatherRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavoriteViewModel(repository) as T
    }
}