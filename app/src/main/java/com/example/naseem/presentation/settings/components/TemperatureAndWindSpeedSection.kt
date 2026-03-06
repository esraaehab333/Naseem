package com.example.naseem.presentation.settings.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.naseem.R
import com.example.naseem.ui.theme.Gray100
import com.example.naseem.ui.theme.PlusJakartaSansFontFamily
import com.example.naseem.ui.theme.White100

@Composable
fun TemperatureAndWindSpeedSection(color: Color) {
    Text(
        text = "UNITS",
        fontFamily = PlusJakartaSansFontFamily,
        fontWeight = FontWeight.SemiBold,
        color = Gray100,
        fontSize = 12.sp,
        letterSpacing = 1.sp
    )
    Spacer(modifier = Modifier.height(14.dp))
    Surface(
        shape = RoundedCornerShape(18.dp),
        color = White100,
        shadowElevation = 1.1.dp,
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ){
            UnitSection(
                title = "Temperature",
                icon = R.drawable.ic_temperature,
                options = listOf("Celsius", "Fahrenheit", "Kelvin"),
                primaryColor = color,
                segmentBackgroundColor = Gray100.copy(alpha = 0.1f)
            )
            Spacer(modifier = Modifier.height(18.dp))
            UnitSection(
                title = "Wind Speed",
                icon = R.drawable.ic_wind,
                options = listOf("m/s", "mph"),
                primaryColor = color,
                segmentBackgroundColor = Gray100.copy(alpha = 0.1f)
            )
        }
    }
}