package com.example.naseem.data.datasource

import android.content.Context
import com.example.naseem.common.ApiState
import com.example.naseem.data.datasource.local.FavWeatherLocalDataSource
import com.example.naseem.data.datasource.remote.WeatherRemoteDataSource
import com.example.naseem.data.entity.FavoriteEntity
import com.example.naseem.data.dto.WeatherResponse
import com.example.naseem.data.dto.ForecastResponse
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

    suspend fun insertWeatherToFav(favorite: FavoriteEntity) {
        localDataSource.insertFavWeather(favorite)
    }

    suspend fun deleteWeatherFromFav(favorite: FavoriteEntity) {
        localDataSource.deleteFavWeather(favorite)
    }

    fun getAllFavWeather(): Flow<List<FavoriteEntity>> {
        return localDataSource.getAllFavWeather()
    }
}