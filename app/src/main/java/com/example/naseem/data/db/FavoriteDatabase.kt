package com.example.naseem.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.naseem.data.datasource.local.FavWeatherDao
import com.example.naseem.data.entity.FavoriteEntity

@Database(entities = [FavoriteEntity::class], version = 2)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun favWeatherDao(): FavWeatherDao

    companion object {
        @Volatile
        private var INSTANCE: FavoriteDatabase? = null
        fun getInstance(context: Context): FavoriteDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteDatabase::class.java,
                    "favorite_db"
                ) .fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}

