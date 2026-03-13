package com.example.naseem.presentation.home.componets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.naseem.data.dto.ForecastResponse
import com.example.naseem.utils.getWeatherIcon


@Composable
fun TodayForecastWeather(color: Color, forecastData: ForecastResponse?, onNext7DaysClick: () -> Unit) {
    var selectedTab by remember { mutableStateOf(0) }
    val todayList = forecastData?.list?.take(8) ?: emptyList()
    val tomorrowList = forecastData?.list?.slice(8..15) ?: emptyList()
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 18.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Row {
                HomeTabButton("Today", selectedTab == 0, { selectedTab = 0 }, color)
                Spacer(modifier = Modifier.width(20.dp))
                HomeTabButton("Tomorrow", selectedTab == 1, { selectedTab = 1 }, color)
            }
            Next7DaysForecastButton(Modifier, color, onNext7DaysClick)
        }

        Spacer(modifier = Modifier.height(20.dp))

        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            val listToDisplay = if (selectedTab == 0) todayList else tomorrowList
            items(listToDisplay) { item ->
                val iconCode = item.weather?.firstOrNull()?.icon
                val displayTime = item.dtTxt?.let { text ->
                    if (text.length >= 16) text.substring(11, 16) else text
                } ?: "--:--"
                ForecastItem(
                    time = displayTime,
                    icon = getWeatherIcon(iconCode),
                    temp = "${item.main?.temp?.toInt() ?: 0}",
                    isSelected = false,
                    color = color
                )
            }
        }
    }
}