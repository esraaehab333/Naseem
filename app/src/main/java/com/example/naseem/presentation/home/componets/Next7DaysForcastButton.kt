package com.example.naseem.presentation.home.componets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.naseem.R

@Composable
fun Next7DaysForecastButton(modifier: Modifier, color: Color){
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(18.dp))
            .clickable {
                println("Navigate to 7 days screen")
            }
            .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Next 7 days",
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            color = color
        )

        Icon(
            painter = painterResource(R.drawable.ic_arrow),
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(10.dp)
        )
    }
}