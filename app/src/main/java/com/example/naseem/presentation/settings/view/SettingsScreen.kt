package com.example.naseem.presentation.settings.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.naseem.R
import com.example.naseem.presentation.settings.components.LanguageSection
import com.example.naseem.presentation.settings.components.LocationSection
import com.example.naseem.presentation.settings.components.TemperatureAndWindSpeedSection
import com.example.naseem.presentation.settings.viewModel.SettingsViewModel
import com.example.naseem.ui.theme.Black100
import com.example.naseem.ui.theme.PlusJakartaSansFontFamily
import com.example.naseem.ui.theme.White100

@Composable
fun SettingsScreen(
    color: Color,
    onLanguageChange: (String) -> Unit,
    settingsViewModel: SettingsViewModel,
    onNavigateToSettingsMap: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White100)
            .padding(24.dp)
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = stringResource(R.string.settings),
            fontFamily = PlusJakartaSansFontFamily,
            fontWeight = FontWeight.Bold,
            color = Black100,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(25.dp))
        LocationSection(
            color = color,
            settingsViewModel = settingsViewModel,
            onNavigateToMap = onNavigateToSettingsMap
        )
        Spacer(modifier = Modifier.height(32.dp))
        TemperatureAndWindSpeedSection(
            color = color,
            settingsViewModel = settingsViewModel
        )
        Spacer(modifier = Modifier.height(32.dp))
        LanguageSection(
            color = color,
            onLanguageChange = onLanguageChange
        )
    }
}