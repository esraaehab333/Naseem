package com.example.naseem.presentation.fav.components

import com.example.naseem.data.entity.FavoriteEntity

data class FavoriteModel(
    val cityName: String,
    val fullAddress: String,
    val latitude: Double,
    val longitude: Double
) {
    constructor(entity: FavoriteEntity) : this(
        cityName = entity.cityName,
        fullAddress = entity.fullAddress,
        latitude = entity.latitude,
        longitude = entity.longitude
    )
    fun toEntity() = FavoriteEntity(
        cityName = cityName,
        fullAddress = fullAddress,
        latitude = latitude,
        longitude = longitude
    )
}