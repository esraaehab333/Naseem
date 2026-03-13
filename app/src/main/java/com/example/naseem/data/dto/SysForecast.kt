package com.example.naseem.data.dto

import com.google.gson.annotations.SerializedName

data class SysForecast(
    @SerializedName("pod")
    val pod: String
)