package com.example.naseem.presentation.fav.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.naseem.common.ApiState
import com.example.naseem.data.datasource.WeatherRepository
import com.example.naseem.data.dto.ForecastResponse
import com.example.naseem.data.dto.WeatherResponse
import com.example.naseem.data.model.FavoriteModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
class FavoriteViewModel(
    context: Context
) : ViewModel() {
    private val repository = WeatherRepository(context.applicationContext)

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

    fun getWeatherForFavoriteLocation(lat: Double, lon: Double) {
        viewModelScope.launch {
            val weatherResult = repository.getCurrentWeather(lat, lon)
            val forecastResult = repository.getFiveDayForecast(lat, lon)

            if (weatherResult is ApiState.Success) _selectedWeather.value = weatherResult.data
            if (forecastResult is ApiState.Success) _selectedForecast.value = forecastResult.data
        }
    }

    init {
        getAllFavorites()
    }

    private fun getAllFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllFavWeather()
                .catch { e ->
                    _favoritesState.value = ApiState.Failure(e)
                }
                .map { list ->
                    list.map { FavoriteModel(it) }
                }
                .collect { models ->
                    _favoritesState.value = ApiState.Success(models)
                }
        }
    }

    fun addToFavorites(favorite: FavoriteModel) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.insertWeatherToFav(favorite.toEntity())
                _addEvent.emit("${favorite.cityName} added to favorites")
            } catch (e: Exception) {
                _addEvent.emit("Something went wrong")
            }
        }
    }

    fun deleteFromFavorites(favorite: FavoriteModel) {
        viewModelScope.launch(Dispatchers.IO) {
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
    private val context: Context
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavoriteViewModel(context.applicationContext) as T
    }
}
