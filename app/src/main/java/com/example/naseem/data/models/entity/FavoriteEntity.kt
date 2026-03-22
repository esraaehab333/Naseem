package com.example.naseem.data.models.entity

import androidx.room.Entity

@Entity(
    tableName = "favorite",
    primaryKeys = ["latitude", "longitude"]
)
data class FavoriteEntity(
    val cityName: String,
    val fullAddress: String,
    val latitude: Double,
    val longitude: Double
)