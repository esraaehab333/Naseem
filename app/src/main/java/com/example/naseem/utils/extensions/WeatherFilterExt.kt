package com.example.naseem.utils.extensions

import android.content.Context
import com.example.naseem.R
import com.example.naseem.utils.WeatherFilter

fun WeatherFilter.displayName(context: Context): String = when (this) {
    WeatherFilter.RAIN         -> context.getString(R.string.heavy_rain)
    WeatherFilter.WIND         -> context.getString(R.string.strong_wind)
    WeatherFilter.SNOW         -> context.getString(R.string.snowfall)
    WeatherFilter.THUNDERSTORM -> context.getString(R.string.thunderstorm)
}
fun WeatherFilter.icon(): Int = when (this) {
    WeatherFilter.RAIN         -> R.drawable.ic_rainy
    WeatherFilter.WIND         -> R.drawable.ic_wind
    WeatherFilter.SNOW         -> R.drawable.ic_snowy
    WeatherFilter.THUNDERSTORM -> R.drawable.ic_thunderstorm
}