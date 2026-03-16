package com.example.naseem.presentation.alert.view

import WeatherTriggerSection
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.naseem.R
import com.example.naseem.presentation.alert.components.AlertTypeSection
import com.example.naseem.presentation.alert.components.ScheduleAndDurationSection
import com.example.naseem.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddWeatherAlertScreen(color: Color, onBackButtonClick: () -> Unit) {
    Scaffold(
        containerColor = White100,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Add Weather Alert",
                        fontWeight = FontWeight.Bold,
                        fontFamily = PlusJakartaSansFontFamily,
                        fontSize = 18.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackButtonClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.back))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(28.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            ScheduleAndDurationSection(color)
            AlertTypeSection(color)
            WeatherTriggerSection(color)
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
