package com.example.naseem.data.datasource.local

import android.content.Context
import com.example.naseem.data.db.AppDatabase
import com.example.naseem.data.model.Weather
import kotlinx.coroutines.flow.Flow

class FavWeatherLocalDataSource(context: Context) {
    private val favWeatherDao: FavWeatherDao = AppDatabase.getInstance(context =context).favWeatherDao()

    suspend fun insertFavWeather(weather: Weather) {
        favWeatherDao.insertFavWeather(weather)
    }

    suspend fun deleteFavWeather(weather: Weather) {
        favWeatherDao.deleteFavWeather(weather)
    }

    fun getAllFavWeather(): Flow<List<Weather>> {
        return favWeatherDao.getAllFavWeathers()
    }
}