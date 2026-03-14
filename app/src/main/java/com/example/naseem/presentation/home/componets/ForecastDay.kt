package com.example.naseem.presentation.home.componets

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class ForecastDay(
    val dayName: String,
    val status: String,
    val high: Int,
    val low: Int,
    val color:Color
)