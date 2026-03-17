package com.example.naseem.utils

import androidx.compose.ui.graphics.Color

data class ForecastDay(
    val dayName: String,
    val status: String,
    val high: Int,
    val low: Int,
    val color:Color
)