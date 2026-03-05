package com.example.naseem.presentation.home.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.naseem.R
import com.example.naseem.presentation.home.componets.TodayForecastWeather
import com.example.naseem.presentation.home.componets.WeatherDetailsCard
import com.example.naseem.presentation.home.componets.WeatherHeaderSection
import com.example.naseem.ui.theme.MildPrimary
import com.example.naseem.ui.theme.NaseemTheme
import com.example.naseem.ui.theme.White100

@Composable
fun HomeScreen(color:Color) {
    Column(modifier = Modifier.fillMaxSize().background(color = White100)) {
        Box(
            modifier = Modifier
                .background(White100)
        ) {
             WeatherHeaderSection(
                 address = "Today , 2 FEB 2026",
                 date = "Today , 2 FEB 2026",
                 image = R.drawable.mild,
                 tempDegree = "19"
             )
             WeatherDetailsCard(
                modifier = Modifier.align(Alignment.BottomCenter).offset(y = 50.dp),
                 color = color
             )
        }
        Spacer(modifier = Modifier.height(80.dp))
        TodayForecastWeather(
            color = color
        )
    }
}
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    NaseemTheme {
        HomeScreen(color = MildPrimary)
    }
}