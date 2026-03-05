package com.example.naseem.presentation.home.componets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.naseem.R

@Composable
fun WeatherDetailsCard(modifier: Modifier = Modifier , color:Color){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(20.dp))
            .background(Color.White, shape = RoundedCornerShape(20.dp))
            .padding(vertical = 18.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            WeatherDetailItem(
                iconRes = R.drawable.ic_humidity,
                label = "Humidity",
                value = "85%",
                color = color
            )
            WeatherDetailItem(
                iconRes = R.drawable.ic_wind,
                label = "Wind",
                value = "9 km/h",
                color = color
            )
            WeatherDetailItem(
                iconRes = R.drawable.ic_cloudy,
                label = "Cloudy",
                value = "Overcast",
                color = color
            )
            WeatherDetailItem(
                iconRes = R.drawable.ic_pressure,
                label = "Humidity",
                value = "85%",
                color = color
            )
        }
    }
}