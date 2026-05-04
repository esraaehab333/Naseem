package com.example.naseem.presentation.settings.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.naseem.data.repository.SettingsRepository
import kotlinx.coroutines.flow.StateFlow

class SettingsViewModel(private val repo: SettingsRepository) : ViewModel() {
    val temperatureUnit: StateFlow<String> = repo.temperatureUnit
    val windSpeedUnit: StateFlow<String> = repo.windSpeedUnit
    val useGps: StateFlow<Boolean> = repo.useGps
    val savedLocationLabel: StateFlow<String?> = repo.savedLocationLabel
    fun setTemperatureUnit(unit: String){
        repo.setTemperatureUnit(unit)
    }
    fun setWindSpeedUnit(unit: String){
        repo.setWindSpeedUnit(unit)
    }
    fun setUseGps(enabled: Boolean){
        repo.setUseGps(enabled)
    }
    fun saveMapLocation(lat: Double, lon: Double, label: String) {
        repo.saveMapLocation(lat, lon, label)
    }
}

class SettingsViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        SettingsViewModel(SettingsRepository.getInstance(context)) as T
}