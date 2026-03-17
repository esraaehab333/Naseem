package com.example.naseem.data.datasource.local.fav

import com.example.naseem.data.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

class FavWeatherLocalDataSource(
    private val favWeatherDao: FavWeatherDao
) {
    suspend fun insertFavWeather(favorite: FavoriteEntity) {
        favWeatherDao.insertFavWeather(favorite)
    }

    suspend fun deleteFavWeather(favorite: FavoriteEntity) {
        favWeatherDao.deleteFavWeather(favorite)
    }

    fun getAllFavWeather(): Flow<List<FavoriteEntity>> {
        return favWeatherDao.getAllFavWeathers()
    }
}