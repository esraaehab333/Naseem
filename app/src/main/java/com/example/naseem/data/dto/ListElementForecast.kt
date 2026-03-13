package com.example.naseem.data.dto

import MainForecast
import com.google.gson.annotations.SerializedName

data class ListElementForecast(
    @SerializedName("dt")
    val dt: Long?,

    @SerializedName("main")
    val main: MainForecast?,

    @SerializedName("weather")
    val weather: List<Weather>?,

    @SerializedName("clouds")
    val clouds: Clouds?,

    @SerializedName("wind")
    val wind: Wind?,

    @SerializedName("visibility")
    val visibility: Long?,

    @SerializedName("pop")
    val pop: Double?,

    @SerializedName("sys")
    val sys: SysForecast?,

    @SerializedName("dt_txt")
    val dtTxt: String?
)