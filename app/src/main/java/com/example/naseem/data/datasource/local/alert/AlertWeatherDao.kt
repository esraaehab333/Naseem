package com.example.naseem.data.datasource.local.alert

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.naseem.data.entity.AlertEntity
import kotlinx.coroutines.flow.Flow
@Dao
interface AlertWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlertWeather(alert: AlertEntity)

    @Delete
    suspend fun deleteAlertWeather(alert: AlertEntity)

    @Query("SELECT * FROM alerts")
    fun getAllAlertWeathers(): Flow<List<AlertEntity>>
}