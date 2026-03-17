package com.example.naseem.utils

import kotlin.math.roundToInt

fun convertTemp(celsiusTemp: Double, unit: String): String {
    return when (unit) {
        "fahrenheit" -> "${(celsiusTemp * 9 / 5 + 32).roundToInt()}°F"
        "kelvin"     -> "${(celsiusTemp + 273.15).roundToInt()} K"
        else         -> "${celsiusTemp.roundToInt()}°C"
    }
}

fun convertWind(mps: Double, unit: String): String {
    return when (unit) {
        "miles_per_hour" -> "${"%.1f".format(mps * 2.237)} mph"
        else             -> "${"%.1f".format(mps)} m/s"
    }
}