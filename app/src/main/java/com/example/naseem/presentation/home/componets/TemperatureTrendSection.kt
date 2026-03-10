package com.example.naseem.presentation.home.componets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.naseem.ui.theme.Black100
import com.example.naseem.ui.theme.Gray100
import com.example.naseem.ui.theme.PlusJakartaSansFontFamily

@Composable
fun TemperatureTrendSection(highTemps: List<Int>, color: Color) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(containerColor = Gray100.copy(alpha = 0.06f))
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text("Temperature Trend",
                        color = Gray100,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp,
                        fontFamily = PlusJakartaSansFontFamily
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                SpanStyle(
                                color = Black100,
                                fontWeight = FontWeight.Bold,
                                fontSize = 28.sp,
                                fontFamily = PlusJakartaSansFontFamily
                            )
                            ) { append("${highTemps.firstOrNull()}° ") }
                            withStyle(
                                SpanStyle(
                                color = Gray100,
                                fontWeight = FontWeight.Normal,
                                fontSize = 28.sp,
                                fontFamily = PlusJakartaSansFontFamily
                            )
                            ) { append("/ 58°") }
                        }
                    )
                }
                Surface(shape = CircleShape, color = color.copy(alpha = 0.1f)) {
                    Text(
                        "Next 7 Days",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        color = color,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = PlusJakartaSansFontFamily
                    )
                }
            }
            ForeCastGraph(highTemps = highTemps , color=color)
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                listOf("MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN").forEach {
                    Text(it,
                        color = Gray100,
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp,
                        fontFamily = PlusJakartaSansFontFamily
                    )
                }
            }
        }
    }
}