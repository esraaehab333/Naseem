package com.example.naseem.presentation.alert.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.example.naseem.R
import com.example.naseem.ui.theme.Black100
import com.example.naseem.ui.theme.PlusJakartaSansFontFamily

@Composable
fun ScheduleAndDurationSection(color: Color){
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_schedule),
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Schedule and Duration",
            fontFamily = PlusJakartaSansFontFamily,
            fontWeight = FontWeight.Bold,
            color = Black100,
            fontSize = 14.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }

}