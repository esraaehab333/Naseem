package com.example.naseem.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.naseem.data.datasource.local.alert.AlertWeatherDao
import com.example.naseem.data.models.entity.AlertEntity

@Database(entities = [AlertEntity::class], version = 1)
abstract class AlertDataBase :RoomDatabase() {
    abstract fun alertWeatherDao():AlertWeatherDao

    companion object {
        @Volatile
        private var INSTANCE: AlertDataBase? = null
        fun getInstance(context: Context): AlertDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AlertDataBase::class.java,
                    "alert_db"
                ) .fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}