package com.example.naseem.data.datasource

import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SettingsRepository private constructor(context: Context) {

    private val prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    // Temperature
    private val _temperatureUnit = MutableStateFlow(
        prefs.getString("temperature_unit", "celsius") ?: "celsius"
    )
    val temperatureUnit: StateFlow<String> = _temperatureUnit

    fun setTemperatureUnit(unit: String) {
        prefs.edit().putString("temperature_unit", unit).apply()
        _temperatureUnit.value = unit
    }

    // Wind Speed
    private val _windSpeedUnit = MutableStateFlow(
        prefs.getString("wind_speed_unit", "meter_per_sec") ?: "meter_per_sec"
    )
    val windSpeedUnit: StateFlow<String> = _windSpeedUnit

    fun setWindSpeedUnit(unit: String) {
        prefs.edit().putString("wind_speed_unit", unit).apply()
        _windSpeedUnit.value = unit
    }

    companion object {
        @Volatile
        private var INSTANCE: SettingsRepository? = null

        fun getInstance(context: Context): SettingsRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: SettingsRepository(context.applicationContext).also {
                    INSTANCE = it
                }
            }
        }
    }
}