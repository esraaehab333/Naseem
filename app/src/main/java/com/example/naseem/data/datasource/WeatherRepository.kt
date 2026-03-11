package com.example.naseem.data.datasource

import android.content.Context
import com.example.naseem.common.ApiState
import com.example.naseem.data.datasource.local.FavWeatherLocalDataSource
import com.example.naseem.data.datasource.remote.WeatherRemoteDataSource
import com.example.naseem.data.model.WeatherResponse
import com.example.naseem.data.entity.WeatherEntity
import com.example.naseem.data.model.ForecastResponse
import kotlinx.coroutines.flow.Flow

class WeatherRepository(context: Context) {
    private val remoteDataSource = WeatherRemoteDataSource()
    private val localDataSource = FavWeatherLocalDataSource(context)

    suspend fun getCurrentWeather(lat: Double, lon: Double, apiKey: String): ApiState<WeatherResponse> {
        return remoteDataSource.getCurrentWeather(lat, lon, apiKey)
    }

    suspend fun getFiveDayForecast(lat: Double, lon: Double, apiKey: String): ApiState<ForecastResponse> {
        return remoteDataSource.getFiveDayForecast(lat, lon, apiKey)
    }

    suspend fun insertWeatherToFav(weather: WeatherEntity) {
        localDataSource.insertFavWeather(weather)
    }

    suspend fun deleteWeatherFromFav(weather: WeatherEntity) {
        localDataSource.deleteFavWeather(weather)
    }

    fun getAllFavWeather(): Flow<List<WeatherEntity>> {
        return localDataSource.getAllFavWeather()
    }
}