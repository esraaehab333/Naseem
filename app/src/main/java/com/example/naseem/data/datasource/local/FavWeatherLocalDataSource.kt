package com.example.naseem.data.datasource.local

import android.content.Context
import com.example.naseem.data.db.AppDatabase
import com.example.naseem.data.entity.WeatherEntity
import kotlinx.coroutines.flow.Flow

class FavWeatherLocalDataSource(context: Context) {
    private val favWeatherDao: FavWeatherDao = AppDatabase.getInstance(context =context).favWeatherDao()

    suspend fun insertFavWeather(weather: WeatherEntity) {
        favWeatherDao.insertFavWeather(weather)
    }

    suspend fun deleteFavWeather(weather: WeatherEntity) {
        favWeatherDao.deleteFavWeather(weather)
    }

    fun getAllFavWeather(): Flow<List<WeatherEntity>> {
        return favWeatherDao.getAllFavWeathers()
    }
}