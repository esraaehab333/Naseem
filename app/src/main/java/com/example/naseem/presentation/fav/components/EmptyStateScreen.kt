package com.example.naseem.presentation.fav.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.naseem.ui.theme.Gray100
import com.example.naseem.ui.theme.PlusJakartaSansFontFamily


@Composable
fun EmptyStateScreen(
    icon:Int,
    title: String,
    subtitle: String,
    color: Color,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            modifier = Modifier.size(80.dp),
            tint = color.copy(alpha = 0.6f)
        )
        Spacer(modifier = Modifier.height(18.dp))
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontFamily = PlusJakartaSansFontFamily,
            fontSize = 16.sp,
            color = Gray100
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = subtitle,
            textAlign = TextAlign.Center,
            fontFamily = PlusJakartaSansFontFamily,
            fontSize = 12.sp,
            color = Gray100.copy(alpha = 0.6f)
        )
    }
}