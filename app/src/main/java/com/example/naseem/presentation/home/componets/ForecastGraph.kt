package com.example.naseem.presentation.home.componets

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun ForeCastGraph(highTemps: List<Int>, color: Color){
    val minTemp = highTemps.minOrNull()?.toFloat() ?: 0f
    val maxTemp = highTemps.maxOrNull()?.toFloat() ?: 100f
    val range = (maxTemp - minTemp).coerceAtLeast(1f)
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .padding(vertical = 16.dp)
    ) {
        val width = size.width
        val height = size.height
        val spacePerPoint = width / (highTemps.size - 1)

        val points = highTemps.mapIndexed { index, temp ->
            val y = height - ((temp - minTemp) / range) * height
            Offset(index * spacePerPoint, y)
        }

        val strokePath = Path().apply {
            if (points.isNotEmpty()) {
                moveTo(points[0].x, points[0].y)
                for (i in 0 until points.size - 1) {
                    val p0 = points[i]
                    val p1 = points[i + 1]
                    val control1 = Offset(p0.x + (p1.x - p0.x) / 2f, p0.y)
                    val control2 = Offset(p0.x + (p1.x - p0.x) / 2f, p1.y)
                    cubicTo(control1.x, control1.y, control2.x, control2.y, p1.x, p1.y)
                }
            }
        }
        val fillPath = Path().apply {
            addPath(strokePath)
            lineTo(width, height)
            lineTo(0f, height)
            close()
        }
        drawPath(
            path = fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(color.copy(alpha = 0.3f), Color.Transparent)
            )
        )
        drawPath(
            path = strokePath,
            color = color,
            style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
        )
    }
}