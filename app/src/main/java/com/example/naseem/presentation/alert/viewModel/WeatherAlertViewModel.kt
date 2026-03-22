package com.example.naseem.presentation.alert.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.naseem.common.ApiState
import com.example.naseem.data.repository.WeatherRepository
import com.example.naseem.data.models.responses.WeatherAlertModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class WeatherAlertViewModel(
    context: Context
) : ViewModel() {

    private val repository = WeatherRepository(context.applicationContext)

    private val _alertsState = MutableStateFlow<ApiState<List<WeatherAlertModel>>>(ApiState.Loading)
    val alertsState: StateFlow<ApiState<List<WeatherAlertModel>>> = _alertsState.asStateFlow()

    private val _deleteEvent = MutableSharedFlow<String>()
    val deleteEvent: SharedFlow<String> = _deleteEvent.asSharedFlow()

    private val _addEvent = MutableSharedFlow<String>()
    val addEvent: SharedFlow<String> = _addEvent.asSharedFlow()

    init {
        getAllAlerts()
    }

    private fun getAllAlerts() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllAlertWeather()
                .catch { e ->
                    _alertsState.value = ApiState.Failure(e)
                }
                .map { list ->
                    list.map { WeatherAlertModel(it) }
                }
                .collect { models ->
                    _alertsState.value = ApiState.Success(models)
                }
        }
    }

    fun addAlert(alert: WeatherAlertModel) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.insertWeatherToAlert(alert.toEntity())
                _addEvent.emit("Alert added successfully")
            } catch (e: Exception) {
                _addEvent.emit("Something went wrong")
            }
        }
    }

    fun deleteAlert(alert: WeatherAlertModel) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.deleteWeatherFromAlert(alert.toEntity())
                _deleteEvent.emit("Alert deleted successfully")
            } catch (e: Exception) {
                _deleteEvent.emit("Something went wrong")
            }
        }
    }
}

class WeatherAlertViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WeatherAlertViewModel(context.applicationContext) as T
    }
}