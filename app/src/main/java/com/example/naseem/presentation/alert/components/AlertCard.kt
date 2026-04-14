package com.example.naseem.presentation.alert.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import com.example.naseem.utils.getRemainingTime

@Composable
fun AlertCard(
    alert: WeatherAlertModel,
    color: Color,
    onDeleteClick: () -> Unit,
    onTurnOffClick: () -> Unit
) {
    val context = LocalContext.current
    val isEnabled by remember(alert.createdAt, alert.isEnabled) {
        mutableStateOf(alert.isEnabled)
    }

    var remainingTime by remember { mutableStateOf(getRemainingTime(alert.targetTimeMillis)) }
    LaunchedEffect(alert.targetTimeMillis) {
        while (true) {
            remainingTime = getRemainingTime(alert.targetTimeMillis)
            kotlinx.coroutines.delay(60_000L)
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isEnabled) "ENABLED" else "DISABLED",
                    fontFamily = PlusJakartaSansFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = if (isEnabled) color else Gray100,
                    fontSize = 10.sp,
                    letterSpacing = 1.sp
                )
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Gray100.copy(alpha = 0.1f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(alert.weatherFilter.icon()),
                        contentDescription = null,
                        tint = if (alert.alertType == "alarm") color else Gray100,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = alert.weatherFilter.displayName(context),
                fontFamily = PlusJakartaSansFontFamily,
                fontWeight = FontWeight.Bold,
                color = Black100,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_schedule),
                        contentDescription = null,
                        tint = Gray100,
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        text = remainingTime,
                        fontFamily = PlusJakartaSansFontFamily,
                        color = Gray100,
                        fontSize = 11.sp
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        painter = painterResource(
                            if (alert.alertType == "alarm") R.drawable.ic_alarm_sound
                            else R.drawable.ic_notification
                        ),
                        contentDescription = null,
                        tint = Gray100,
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        text = if (alert.alertType == "alarm") "Alarm Sound" else "Notification Only",
                        fontFamily = PlusJakartaSansFontFamily,
                        color = Gray100,
                        fontSize = 11.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = onDeleteClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp),
                    shape = RoundedCornerShape(50.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Gray100.copy(alpha = 0.1f),
                        contentColor = Black100
                    ),
                    border = null
                ) {
                    Text(
                        text = "Delete",
                        fontFamily = PlusJakartaSansFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }

                Button(
                    onClick = onTurnOffClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp),
                    shape = RoundedCornerShape(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isEnabled) color else Gray100.copy(alpha = 0.3f),
                        contentColor = Color.White
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
                ) {
                    Text(
                        text = if (isEnabled) "Turn Off" else "Turn On",
                        fontFamily = PlusJakartaSansFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}