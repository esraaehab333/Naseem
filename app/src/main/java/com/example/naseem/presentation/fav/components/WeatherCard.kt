package com.example.naseem.presentation.fav.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.naseem.R
import com.example.naseem.ui.theme.Black100
import com.example.naseem.ui.theme.Gray100
import com.example.naseem.ui.theme.PlusJakartaSansFontFamily
import com.example.naseem.ui.theme.White100

@Composable
fun WeatherCard(place: SavedPlace,color: Color) {
    OutlinedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.outlinedCardColors(containerColor = White100),
        border = CardDefaults.outlinedCardBorder().copy(
            brush = SolidColor(Gray100.copy(alpha = 0.5f)),
            width = 1.dp
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(50.dp),
                shape = RoundedCornerShape(16.dp),
                color = color.copy(alpha = 0.1f)
            ) {
                Icon(
                    painter = painterResource(place.icon),
                    contentDescription = null,
                    modifier = Modifier.padding(15.dp),
                    tint =color
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = place.city,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Black100,
                    fontFamily = PlusJakartaSansFontFamily
                )
                Text(
                    text = "${place.temperature} • ${place.condition}",
                    fontSize = 12.sp,
                    color = Gray100,
                    fontFamily = PlusJakartaSansFontFamily,
                    fontWeight = FontWeight.Normal
                )
            }
            IconButton(onClick = { }) {
                Icon(
                    painter = painterResource(R.drawable.ic_delete),
                    contentDescription = "Delete",
                    tint = Gray100
                )
            }
        }
    }
}