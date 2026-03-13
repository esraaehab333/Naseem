package com.example.naseem.data.dto

import com.google.gson.annotations.SerializedName

data class ForecastResponse(
    @SerializedName("cod")
    val cod: String?,

    @SerializedName("message")
    val message: Long?,

    @SerializedName("cnt")
    val cnt: Long?,

    @SerializedName("list")
    val list: List<ListElementForecast>?,

    @SerializedName("city")
    val city: CityForecast?
)