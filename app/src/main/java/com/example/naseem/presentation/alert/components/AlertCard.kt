package com.example.naseem.presentation.alert.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.naseem.R
import com.example.naseem.data.models.responses.WeatherAlertModel
import com.example.naseem.ui.theme.Black100
import com.example.naseem.ui.theme.Gray100
import com.example.naseem.ui.theme.PlusJakartaSansFontFamily
import com.example.naseem.utils.extensions.displayName
import com.example.naseem.utils.extensions.icon

@Composable
fun AlertCard(
    alert: WeatherAlertModel,
    color: Color,
    onDeleteClick: () -> Unit
) {
    val context = LocalContext.current

    val hasTriggered = remember(alert.createdAt) {
        context.getSharedPreferences("triggered_alerts", android.content.Context.MODE_PRIVATE)
            .getBoolean("triggered_${alert.createdAt}", false)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (hasTriggered) color.copy(alpha = 0.14f) else color.copy(alpha = 0.06f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .background(color.copy(alpha = 0.12f), RoundedCornerShape(14.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(alert.weatherFilter.icon()),
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(26.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = alert.weatherFilter.displayName(context),
                        fontFamily = PlusJakartaSansFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = Black100,
                        fontSize = 15.sp
                    )

                    if (hasTriggered) {
                        TriggeredBadge()
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_schedule),
                        contentDescription = null,
                        tint = Gray100,
                        modifier = Modifier.size(12.dp)
                    )
                    Text(
                        text = alert.dateLabel,
                        fontFamily = PlusJakartaSansFontFamily,
                        color = Gray100,
                        fontSize = 12.sp
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.padding(top = 2.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_alert_type),
                        contentDescription = null,
                        tint = Gray100,
                        modifier = Modifier.size(12.dp)
                    )
                    Text(
                        text = "${alert.fromLabel} – ${alert.toLabel}",
                        fontFamily = PlusJakartaSansFontFamily,
                        color = Gray100,
                        fontSize = 12.sp
                    )
                }
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                AlertTypeBadge(alertType = alert.alertType, color = color)

                IconButton(onClick = onDeleteClick) {
                    Icon(
                        painter = painterResource(R.drawable.ic_delete),
                        contentDescription = stringResource(R.string.delete_alert),
                        tint = Color.Red.copy(alpha = 0.7f),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun TriggeredBadge() {
    Box(
        modifier = Modifier
            .background(
                color = Color(0xFFFF6B35).copy(alpha = 0.15f),
                shape = RoundedCornerShape(6.dp)
            )
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .background(Color(0xFFFF6B35), RoundedCornerShape(50.dp))
            )
            Text(
                text = stringResource(R.string.triggered),
                fontFamily = PlusJakartaSansFontFamily,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFFFF6B35),
                fontSize = 9.sp
            )
        }
    }
}

@Composable
private fun AlertTypeBadge(alertType: String, color: Color) {
    Box(
        modifier = Modifier
            .background(color.copy(alpha = 0.12f), RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                painter = painterResource(
                    if (alertType == "alarm") R.drawable.ic_alarm_sound
                    else R.drawable.ic_notification
                ),
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(12.dp)
            )
            Text(
                text = alertType.replaceFirstChar { it.uppercase() },
                fontFamily = PlusJakartaSansFontFamily,
                fontWeight = FontWeight.SemiBold,
                color = color,
                fontSize = 10.sp
            )
        }
    }
}