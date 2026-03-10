package com.example.naseem.data.model

import com.google.gson.annotations.SerializedName

data class CityForecast (
    @SerializedName("id")
    val id: Long,

    @SerializedName("name")
    val name: String,

    @SerializedName("coord")
    val coord: Coord,

    @SerializedName("country")
    val country: String,

    @SerializedName("population")
    val population: Long,

    @SerializedName("timezone")
    val timezone: Long,

    @SerializedName("sunrise")
    val sunrise: Long,

    @SerializedName("sunset")
    val sunset: Long
)