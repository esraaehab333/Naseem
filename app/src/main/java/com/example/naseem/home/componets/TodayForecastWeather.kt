package com.example.naseem.home.componets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.naseem.R


@Composable
fun TodayForecastWeather(modifier: Modifier = Modifier, color: Color) {
    var selectedTab by remember { mutableStateOf(0) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.Bottom) {
                HomeTabButton(
                    text = "Today",
                    isSelected = selectedTab == 0,
                    onClick = { selectedTab = 0},
                    color = color
                )

                Spacer(modifier = Modifier.width(20.dp))

                HomeTabButton(
                    text = "Tomorrow",
                    isSelected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    color = color
                )
            }
            Next7DaysForecastButton(modifier, color)
        }

        Spacer(modifier = Modifier.height(20.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 10.dp)
        ) {
            if (selectedTab == 0) {
                items(12) { index ->
                    ForecastItem(
                        time = "${(22 + index) % 24}:00",
                        icon = R.drawable.ic_cloudy,
                        temp = "${19 - index}°",
                        isSelected = index == 0,
                        color = color
                    )
                }
            } else {
                items(12) { index ->
                    ForecastItem(
                        time = "${index}:00",
                        icon = R.drawable.ic_cloudy,
                        temp = "${15 + index}°",
                        isSelected = false,
                        color = color
                    )
                }
            }
        }
    }
}