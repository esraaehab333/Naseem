package com.example.naseem.utils.extensions

import com.example.naseem.R
import com.example.naseem.utils.WeatherFilter

fun WeatherFilter.icon(): Int = when (this) {
    WeatherFilter.RAIN         -> R.drawable.ic_rainy
    WeatherFilter.WIND         -> R.drawable.ic_wind
    WeatherFilter.SNOW         -> R.drawable.ic_snowy
    WeatherFilter.THUNDERSTORM -> R.drawable.ic_thunderstorm
}

fun WeatherFilter.displayName(): String = when (this) {
    WeatherFilter.RAIN         -> "Heavy Rain"
    WeatherFilter.WIND         -> "Strong Wind"
    WeatherFilter.SNOW         -> "Snowfall"
    WeatherFilter.THUNDERSTORM -> "Thunderstorm"
}