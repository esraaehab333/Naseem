package com.example.naseem.data.models.entity


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.naseem.utils.WeatherFilter

@Entity(tableName = "alerts")
data class AlertEntity(
    @PrimaryKey
    val createdAt: Long = System.currentTimeMillis(),
    val fromTimeMillis: Long,
    val toTimeMillis: Long,
    val dateLabel: String,
    val fromLabel: String,
    val toLabel: String,
    val alertType: String,
    val weatherFilter: WeatherFilter,
    val latitude: Double,
    val longitude: Double,
)