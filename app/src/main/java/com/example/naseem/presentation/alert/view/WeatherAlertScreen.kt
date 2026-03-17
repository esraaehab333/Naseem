package com.example.naseem.presentation.alert.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.naseem.common.ApiState
import com.example.naseem.data.model.WeatherAlertModel
import com.example.naseem.presentation.alert.components.AlertScheduler
import com.example.naseem.presentation.alert.viewModel.WeatherAlertViewModel
import com.example.naseem.presentation.fav.components.EmptyStateScreen
import com.example.naseem.ui.theme.Black100
import com.example.naseem.ui.theme.Gray100
import com.example.naseem.ui.theme.PlusJakartaSansFontFamily
import com.example.naseem.ui.theme.White100
import com.example.naseem.utils.WeatherFilter

@Composable
fun WeatherAlertScreen(
    color: Color,
    viewModel: WeatherAlertViewModel,
    onFloatingActionButtonClicked: () -> Unit
) {
    val state by viewModel.alertsState.collectAsState()
    val context = LocalContext.current

    Scaffold(
        containerColor = Color.Transparent,
        floatingActionButton = {
            FloatingActionButton(
                onClick = onFloatingActionButtonClicked,
                containerColor = color,
                contentColor = White100,
                shape = RoundedCornerShape(50.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_add),
                    tint = White100,
                    modifier = Modifier.size(26.dp),
                    contentDescription = null
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Weather Alerts",
                fontFamily = PlusJakartaSansFontFamily,
                fontWeight = FontWeight.Bold,
                color = Black100,
                fontSize = 18.sp,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Text(
                text = "Get notified when severe weather hits",
                fontSize = 12.sp,
                color = Gray100,
                fontFamily = PlusJakartaSansFontFamily,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            when (state) {
                is ApiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = color)
                    }
                }

                is ApiState.Success -> {
                    val alerts = (state as ApiState.Success).data
                    if (alerts.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            EmptyStateScreen(
                                icon = R.drawable.ic_alert_type,
                                title = "No Alerts Yet",
                                subtitle = "Tap + to add your first weather alert",
                                color = color
                            )
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            contentPadding = PaddingValues(bottom = 80.dp)
                        ) {
                            items(
                                items = alerts,
                                key = { it.createdAt }
                            ) { alert ->
                                AlertCard(
                                    alert = alert,
                                    color = color,
                                    onDeleteClick = {
                                        AlertScheduler.cancelAlert(context, alert.createdAt)
                                        viewModel.deleteAlert(alert)
                                    }
                                )
                            }
                        }
                    }
                }

                is ApiState.Failure -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            stringResource(R.string.something_went_wrong),
                            color = Color.Red,
                            fontFamily = PlusJakartaSansFontFamily
                        )
                    }
                }
            }
        }
    }
}


// ── helpers ──────────────────────────────────────────────────────────────────

private fun WeatherFilter.icon(): Int = when (this) {
    WeatherFilter.RAIN         -> R.drawable.ic_rainy
    WeatherFilter.WIND         -> R.drawable.ic_wind
    WeatherFilter.SNOW         -> R.drawable.ic_snowy
    WeatherFilter.THUNDERSTORM -> R.drawable.ic_thunderstorm
}

private fun WeatherFilter.displayName(): String = when (this) {
    WeatherFilter.RAIN         -> "Heavy Rain"
    WeatherFilter.WIND         -> "Strong Wind"
    WeatherFilter.SNOW         -> "Snowfall"
    WeatherFilter.THUNDERSTORM -> "Thunderstorm"
}


// ── AlertCard ────────────────────────────────────────────────────────────────

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
            containerColor = if (hasTriggered)
                color.copy(alpha = 0.14f)
            else
                color.copy(alpha = 0.06f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // weather icon box
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

                // title row + triggered chip
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = alert.weatherFilter.displayName(),
                        fontFamily = PlusJakartaSansFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = Black100,
                        fontSize = 15.sp
                    )

                    if (hasTriggered) {
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
                                        .background(
                                            Color(0xFFFF6B35),
                                            RoundedCornerShape(50.dp)
                                        )
                                )
                                Text(
                                    text = "Triggered",
                                    fontFamily = PlusJakartaSansFontFamily,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFFFF6B35),
                                    fontSize = 9.sp
                                )
                            }
                        }
                    }
                }

                // date row
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

                // time range row
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

            // badge + delete
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
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
                                if (alert.alertType == "alarm") R.drawable.ic_alarm_sound
                                else R.drawable.ic_notification
                            ),
                            contentDescription = null,
                            tint = color,
                            modifier = Modifier.size(12.dp)
                        )
                        Text(
                            text = alert.alertType.replaceFirstChar { it.uppercase() },
                            fontFamily = PlusJakartaSansFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            color = color,
                            fontSize = 10.sp
                        )
                    }
                }

                IconButton(onClick = onDeleteClick) {
                    Icon(
                        painter = painterResource(R.drawable.ic_delete),
                        contentDescription = "Delete Alert",
                        tint = Color.Red.copy(alpha = 0.7f),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}