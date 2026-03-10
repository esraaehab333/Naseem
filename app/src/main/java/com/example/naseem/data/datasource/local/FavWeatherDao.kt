package com.example.naseem.data.datasource.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.naseem.data.model.Weather
import kotlinx.coroutines.flow.Flow

@Dao
interface FavWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavWeather(weather:Weather)

    @Delete
    suspend fun deleteFavWeather(weather: Weather)

    @Query("SELECT * FROM weather")
    fun getAllFavWeathers(): Flow<List<Weather>>
}