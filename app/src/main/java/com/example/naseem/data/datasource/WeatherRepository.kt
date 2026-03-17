package com.example.naseem.data.datasource

import android.content.Context
import com.example.naseem.BuildConfig
import com.example.naseem.common.ApiState
import com.example.naseem.data.datasource.local.alert.AlertWeatherDao
import com.example.naseem.data.datasource.local.alert.AlertWeatherLocalDataSource
import com.example.naseem.data.datasource.local.fav.FavWeatherLocalDataSource
import com.example.naseem.data.datasource.remote.WeatherRemoteDataSource
import com.example.naseem.data.entity.FavoriteEntity
import com.example.naseem.data.dto.WeatherResponse
import com.example.naseem.data.dto.ForecastResponse
import com.example.naseem.data.entity.AlertEntity
import kotlinx.coroutines.flow.Flow

class WeatherRepository(context: Context) {
    private val remoteDataSource = WeatherRemoteDataSource()
    private val favLocalDataSource = FavWeatherLocalDataSource(context)
    private val alertLocalDataSource = AlertWeatherLocalDataSource(context)
    val apiKey = BuildConfig.WEATHER_API_KEY
    suspend fun getCurrentWeather(lat: Double, lon: Double): ApiState<WeatherResponse> {
        return remoteDataSource.getCurrentWeather(lat, lon, apiKey)
    }

    suspend fun getFiveDayForecast(lat: Double, lon: Double): ApiState<ForecastResponse> {
        return remoteDataSource.getFiveDayForecast(lat, lon, apiKey)
    }

    suspend fun insertWeatherToFav(favorite: FavoriteEntity) {
        favLocalDataSource.insertFavWeather(favorite)
    }

    suspend fun deleteWeatherFromFav(favorite: FavoriteEntity) {
        favLocalDataSource.deleteFavWeather(favorite)
    }

    fun getAllFavWeather(): Flow<List<FavoriteEntity>> {
        return favLocalDataSource.getAllFavWeather()
    }

    suspend fun insertWeatherToAlert(alert: AlertEntity) {
        alertLocalDataSource.insertAlertWeather(alert)
    }

    suspend fun deleteWeatherFromAlert(alert: AlertEntity) {
        alertLocalDataSource.deleteAlertWeather(alert)
    }

    fun getAllAlertWeather(): Flow<List<AlertEntity>> {
        return alertLocalDataSource.getAllAlertWeather()
    }
}