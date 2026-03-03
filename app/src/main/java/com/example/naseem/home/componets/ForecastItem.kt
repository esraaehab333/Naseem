package com.example.naseem.home.componets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.naseem.ui.theme.White100


@Composable
fun ForecastItem(time: String, icon: Int, temp: String, isSelected: Boolean, color: Color) {
    val contentColor = if (isSelected) White100 else color
    val backgroundColor = if (isSelected) color else White100
    val strokeColor = if (isSelected) Color.Transparent else color.copy(alpha = 0.2f)

    Column(
        modifier = Modifier
            .width(66.dp)
            .shadow(
                elevation = if (isSelected) 10.dp else 0.dp,
                shape = RoundedCornerShape(15.dp),
                spotColor = color
            )
            .border(
                width = 1.dp,
                color = strokeColor,
                shape = RoundedCornerShape(15.dp)
            )
            .background(backgroundColor, shape = RoundedCornerShape(15.dp))
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(35.dp)
    ) {
        Text(
            text = time,
            color = if (isSelected) Color.White.copy(alpha = 0.8f) else Color.Gray,
            fontSize = 12.sp
        )
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = contentColor,
            modifier = Modifier.size(28.dp)
        )
        Text(
            text = "$temp°",
            color = contentColor,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    }
}