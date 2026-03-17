package com.example.naseem.presentation.settings.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.naseem.data.datasource.SettingsRepository
import kotlinx.coroutines.flow.StateFlow

class SettingsViewModel(private val repo: SettingsRepository) : ViewModel() {

    val temperatureUnit: StateFlow<String> = repo.temperatureUnit
    val windSpeedUnit: StateFlow<String> = repo.windSpeedUnit

    fun setTemperatureUnit(unit: String) = repo.setTemperatureUnit(unit)
    fun setWindSpeedUnit(unit: String) = repo.setWindSpeedUnit(unit)
}

class SettingsViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SettingsViewModel(
            SettingsRepository.getInstance(context)
        ) as T
    }
}