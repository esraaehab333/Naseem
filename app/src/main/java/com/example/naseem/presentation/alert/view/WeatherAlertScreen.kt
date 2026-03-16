package com.example.naseem.presentation.alert.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.naseem.R
import com.example.naseem.common.ApiState
import com.example.naseem.data.model.WeatherAlertModel
import com.example.naseem.presentation.alert.viewModel.WeatherAlertViewModel
import com.example.naseem.presentation.fav.components.EmptyStateScreen
import com.example.naseem.ui.theme.Black100
import com.example.naseem.ui.theme.Gray100
import com.example.naseem.ui.theme.PlusJakartaSansFontFamily
import com.example.naseem.ui.theme.White100

@Composable
fun WeatherAlertScreen(
    color: Color,
    viewModel: WeatherAlertViewModel,
    onFloatingActionButtonClicked: () -> Unit
) {
    val state by viewModel.alertsState.collectAsState()

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
                text = "TEEEEEt",
                fontFamily = PlusJakartaSansFontFamily,
                fontWeight = FontWeight.Bold,
                color = Black100,
                fontSize = 18.sp,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Text(
                text = "Test",
                fontSize = 12.sp,
                color = Gray100,
                fontFamily = PlusJakartaSansFontFamily,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            when (state) {
                is ApiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
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
                                icon = R.drawable.ic_alert,
                                title = "Empty",
                                subtitle = "subtitle",
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
                                        viewModel.deleteAlert(alert)
                                    }
                                )
                            }
                        }
                    }
                }

                is ApiState.Failure -> {
                    Text(stringResource(R.string.something_went_wrong), color = Color.Red)
                }
            }
        }
    }
}

@Composable
fun AlertCard(
    alert: WeatherAlertModel,
    color: Color,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.08f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = color.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(
                        when (alert.alertType) {
                            "alarm" -> R.drawable.ic_alarm_sound
                            else -> R.drawable.ic_notification
                        }
                    ),
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Text info
            Column(modifier = Modifier.weight(1f)) {
                // Alert type badge
                Box(
                    modifier = Modifier
                        .background(
                            color = color.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(6.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = alert.alertType.replaceFirstChar { it.uppercase() },
                        fontFamily = PlusJakartaSansFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = color,
                        fontSize = 10.sp
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Date
                Text(
                    text = alert.dateLabel,
                    fontFamily = PlusJakartaSansFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Black100,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(2.dp))

                // Time range
                Text(
                    text = "${alert.fromLabel} - ${alert.toLabel}",
                    fontFamily = PlusJakartaSansFontFamily,
                    color = Gray100,
                    fontSize = 12.sp
                )
            }

            // Delete button
            IconButton(onClick = onDeleteClick) {
                Icon(
                    painter = painterResource(R.drawable.ic_delete),
                    contentDescription = "Delete Alert",
                    tint = Color.Red.copy(alpha = 0.7f),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}