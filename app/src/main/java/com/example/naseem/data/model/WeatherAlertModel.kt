package com.example.naseem.data.model

import com.example.naseem.data.entity.AlertEntity
import com.example.naseem.utils.WeatherFilter

data class WeatherAlertModel(
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
) {
    constructor(entity: AlertEntity) : this(
        createdAt = entity.createdAt,
        fromTimeMillis = entity.fromTimeMillis,
        toTimeMillis = entity.toTimeMillis,
        dateLabel = entity.dateLabel,
        fromLabel = entity.fromLabel,
        toLabel = entity.toLabel,
        alertType = entity.alertType,
        weatherFilter = entity.weatherFilter,
        latitude = entity.latitude,
        longitude = entity.longitude,
    )

    fun toEntity() = AlertEntity(
        createdAt = createdAt,
        fromTimeMillis = fromTimeMillis,
        toTimeMillis = toTimeMillis,
        dateLabel = dateLabel,
        fromLabel = fromLabel,
        toLabel = toLabel,
        alertType = alertType,
        weatherFilter = weatherFilter,
        latitude = latitude,
        longitude = longitude,
    )
}