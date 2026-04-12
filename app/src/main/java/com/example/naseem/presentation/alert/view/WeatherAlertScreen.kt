package com.example.naseem.presentation.alert.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
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
import com.example.naseem.presentation.alert.components.AlertCard
import com.example.naseem.presentation.alert.viewModel.WeatherAlertViewModel
import com.example.naseem.presentation.fav.components.EmptyStateScreen
import com.example.naseem.ui.theme.Black100
import com.example.naseem.ui.theme.Gray100
import com.example.naseem.ui.theme.PlusJakartaSansFontFamily
import com.example.naseem.ui.theme.White100
import com.example.naseem.worker.AlertScheduler

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
                text = stringResource(R.string.weather_alerts),
                fontFamily = PlusJakartaSansFontFamily,
                fontWeight = FontWeight.Bold,
                color = Black100,
                fontSize = 18.sp,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Text(
                text = stringResource(R.string.weather_alerts_subtitle),
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
                                title = stringResource(R.string.no_alerts_yet),
                                subtitle = stringResource(R.string.no_alerts_subtitle),
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
                            text = stringResource(R.string.something_went_wrong),
                            color = Color.Red,
                            fontFamily = PlusJakartaSansFontFamily
                        )
                    }
                }
            }
        }
    }
}