package com.example.naseem.data.repository

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SettingsRepository private constructor(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
    private val _temperatureUnit = MutableStateFlow(prefs.getString("temp_unit", "Celsius") ?: "Celsius")
    val temperatureUnit: StateFlow<String> = _temperatureUnit
    fun setTemperatureUnit(unit: String) {
        prefs.edit().putString("temp_unit", unit).apply()
        _temperatureUnit.value = unit
    }
    private val _windSpeedUnit = MutableStateFlow(prefs.getString("wind_unit", "m/s") ?: "m/s")
    val windSpeedUnit: StateFlow<String> = _windSpeedUnit
    fun setWindSpeedUnit(unit: String) {
        prefs.edit().putString("wind_unit", unit).apply()
        _windSpeedUnit.value = unit
    }
    private val _useGps = MutableStateFlow(prefs.getBoolean("use_gps", true))
    val useGps: StateFlow<Boolean> = _useGps
    fun setUseGps(enabled: Boolean) {
        prefs.edit().putBoolean("use_gps", enabled).apply()
        _useGps.value = enabled
    }
    private val _savedLat = MutableStateFlow(
        prefs.getString("saved_lat", null)?.toDoubleOrNull()
    )
    val savedLat: StateFlow<Double?> = _savedLat
    private val _savedLon = MutableStateFlow(
        prefs.getString("saved_lon", null)?.toDoubleOrNull()
    )
    val savedLon: StateFlow<Double?> = _savedLon
    private val _savedLocationLabel = MutableStateFlow(
        prefs.getString("saved_location_label", null)
    )
    val savedLocationLabel: StateFlow<String?> = _savedLocationLabel
    fun saveMapLocation(lat: Double, lon: Double, label: String) {
        prefs.edit()
            .putString("saved_lat", lat.toString())
            .putString("saved_lon", lon.toString())
            .putString("saved_location_label", label)
            .apply()
        _savedLat.value = lat
        _savedLon.value = lon
        _savedLocationLabel.value = label
    }
    companion object {
        @Volatile private var INSTANCE: SettingsRepository? = null
        fun getInstance(context: Context): SettingsRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: SettingsRepository(context.applicationContext).also { INSTANCE = it }
            }
    }
}