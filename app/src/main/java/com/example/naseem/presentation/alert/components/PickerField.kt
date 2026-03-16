package com.example.naseem.presentation.alert.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.naseem.ui.theme.Black100
import com.example.naseem.ui.theme.Gray100
import com.example.naseem.ui.theme.PlusJakartaSansFontFamily

@Composable
fun PickerField(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontFamily = PlusJakartaSansFontFamily,
            fontWeight = FontWeight.Normal,
            color = Gray100,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Surface(
            onClick = onClick,
            shape = RoundedCornerShape(16.dp),
            color = Gray100.copy(0.05f),
            border = BorderStroke(1.dp,Gray100.copy(0.1f),)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = value,
                    fontSize = 14.sp,
                    fontFamily = PlusJakartaSansFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = Black100,
                )
                Icon(
                    //todo:icon in R
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = Gray100,
                )
            }
        }
    }
}