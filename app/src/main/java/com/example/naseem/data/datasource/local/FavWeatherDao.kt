package com.example.naseem.data.datasource.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.naseem.data.entity.WeatherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavWeather(weather: WeatherEntity)

    @Delete
    suspend fun deleteFavWeather(weather: WeatherEntity)

    @Query("SELECT * FROM weather")
    fun getAllFavWeathers(): Flow<List<WeatherEntity>>
}