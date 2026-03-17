package com.example.naseem.utils

import kotlin.math.roundToInt

fun convertTemp(kelvinTemp: Double, unit: String): String {
    return when (unit) {
        "celsius"    -> "${(kelvinTemp - 273.15).roundToInt()}°C"
        "fahrenheit" -> "${((kelvinTemp - 273.15) * 9 / 5 + 32).roundToInt()}°F"
        else         -> "${kelvinTemp.roundToInt()} K"
    }
}

fun convertWind(mps: Double, unit: String): String {
    return when (unit) {
        "miles_per_hour" -> "${"%.1f".format(mps * 2.237)} mph"
        else             -> "${"%.1f".format(mps)} m/s"
    }
}