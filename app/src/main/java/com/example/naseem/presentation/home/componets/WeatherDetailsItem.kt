package com.example.naseem.presentation.home.componets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.naseem.ui.theme.Black100
import com.example.naseem.ui.theme.Gray100

@Composable
fun WeatherDetailItem(iconRes: Int, label: String, value: String , color: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = label,
            tint = color,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = label,
            color = Gray100,
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = value,
            color = Black100,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}