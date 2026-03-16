package com.example.naseem.presentation.alert.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.naseem.ui.theme.Gray100
import com.example.naseem.ui.theme.PlusJakartaSansFontFamily

@Composable
fun AlertDetails(location:String, timeRange:String, color: Color){
    Column(
        modifier = Modifier.padding(start = 10.dp, top = 4.dp)
    ) {
        Text(
            text = "Today • $location",
            color = Gray100,
            fontWeight = FontWeight.Normal,
            fontFamily = PlusJakartaSansFontFamily,
            fontSize = 12.sp
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 4.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Notifications,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = color
            )
            Text(
                text = " $timeRange",
                color = Gray100,
                fontWeight = FontWeight.Normal,
                fontFamily = PlusJakartaSansFontFamily,
                fontSize = 12.sp
            )
        }
    }
}