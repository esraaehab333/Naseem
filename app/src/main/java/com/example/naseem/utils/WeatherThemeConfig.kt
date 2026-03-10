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
        temp <= 0 -> WeatherThemeConfig(ExtremeColdPrimary, R.drawable.extremecold)
        temp <= 11.3 -> WeatherThemeConfig(ColdPrimary, R.drawable.cold)
        temp <= 22.6 -> WeatherThemeConfig(MildPrimary, R.drawable.mild)
        temp <= 33.9 -> WeatherThemeConfig(WarmPrimary, R.drawable.warm)
        temp <= 45.2 -> WeatherThemeConfig(HotPrimary, R.drawable.hot)
        else -> WeatherThemeConfig(ExtremeHotPrimary, R.drawable.extremehot)
    }
}
fun formatUnixTimestamp(timestamp: Long): String {
    val sdf = java.text.SimpleDateFormat("EEEE, dd MMM", java.util.Locale.getDefault())
    val date = java.util.Date(timestamp * 1000)
    return sdf.format(date)
}