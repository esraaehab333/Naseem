package com.example.naseem.presentation.fav.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.naseem.R
import com.example.naseem.ui.theme.White100

@Composable
fun LocationFloatingActionButton(
    color: Color,
    onClick: () -> Unit
) {
    FloatingActionButton(
        onClick = { onClick() },
        containerColor = color,
        contentColor = Color.White,
        shape = RoundedCornerShape(16.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_current_location),
            modifier = Modifier.size(26.dp),
            tint = White100,
            contentDescription = "My Location"
        )
    }
}