package com.example.naseem.data.datasource.local.fav

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.naseem.data.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavWeather(favorite: FavoriteEntity)

    @Delete
    suspend fun deleteFavWeather(favorite: FavoriteEntity)

    @Query("SELECT * FROM favorite")
    fun getAllFavWeathers(): Flow<List<FavoriteEntity>>
}