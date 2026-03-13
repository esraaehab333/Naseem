package com.example.naseem.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.naseem.R
import com.example.naseem.ui.theme.ColdPrimary
import com.example.naseem.ui.theme.ExtremeColdPrimary
import com.example.naseem.ui.theme.ExtremeHotPrimary
import com.example.naseem.ui.theme.HotPrimary
import com.example.naseem.ui.theme.MildPrimary
import com.example.naseem.ui.theme.WarmPrimary

data class WeatherThemeConfig(
    val color: Color,
    val imageRes: Int
)

@Composable
fun getThemeConfig(temp: Double): WeatherThemeConfig {
    return when {
        temp <= 0  -> WeatherThemeConfig(ExtremeColdPrimary,R.drawable.extremecold)
        temp <= 15 -> WeatherThemeConfig(ColdPrimary,R.drawable.cold)
        temp <= 25 -> WeatherThemeConfig(MildPrimary,R.drawable.mild)
        temp <= 35 -> WeatherThemeConfig(WarmPrimary,R.drawable.warm)
        temp <= 45 -> WeatherThemeConfig(HotPrimary,R.drawable.hot)
        else -> WeatherThemeConfig(ExtremeHotPrimary,R.drawable.extremehot)
    }
}
fun formatUnixTimestamp(timestamp: Long): String {
    val sdf = java.text.SimpleDateFormat("EEEE, dd MMM", java.util.Locale.getDefault())
    val date = java.util.Date(timestamp * 1000)
    return sdf.format(date)
}
fun formatUnixToDay(timestamp: Long): String {
    val sdf = java.text.SimpleDateFormat("EEEE", java.util.Locale.getDefault())
    return sdf.format(java.util.Date(timestamp * 1000))
}
fun getWeatherIcon(iconCode: String?): Int {
    return when (iconCode) {
        "01d" -> R.drawable.ic_sunny
        "01n" -> R.drawable.ic_clear
        "02d", "02n", "03d", "03n", "04d", "04n" -> R.drawable.ic_cloudy
        "09d", "09n", "10d", "10n" -> R.drawable.ic_rainy
        "11d", "11n" -> R.drawable.ic_thunderstorm
        "13d", "13n" -> R.drawable.ic_snowy
        "50d", "50n" -> R.drawable.ic_fog
        else -> R.drawable.ic_cloudy
    }
}