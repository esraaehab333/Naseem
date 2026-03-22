package com.example.naseem.data.models.dto

import com.google.gson.annotations.SerializedName

data class SysWeather(
    @SerializedName("country")
    val country: String?,

    @SerializedName("sunrise")
    val sunrise: Long?,

    @SerializedName("sunset")
    val sunset: Long?
)