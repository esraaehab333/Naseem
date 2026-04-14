package com.example.naseem.presentation.alert.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.naseem.R
import com.example.naseem.ui.theme.Black100
import com.example.naseem.ui.theme.ColdPrimary
import com.example.naseem.ui.theme.HotPrimary
import com.example.naseem.ui.theme.HotSecondary
import com.example.naseem.ui.theme.MildPrimary
import com.example.naseem.ui.theme.PlusJakartaSansFontFamily
import com.example.naseem.utils.WeatherFilter

@Composable
fun WeatherTriggerSection(
    color: Color,
    onFilterSelected: (WeatherFilter?) -> Unit
) {
    var selectedFilter by remember { mutableStateOf<WeatherFilter?>(null) }

    fun onToggle(filter: WeatherFilter, checked: Boolean) {
        selectedFilter = if (checked) filter else null
        onFilterSelected(selectedFilter)
    }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(R.drawable.ic_weather_triggers),
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = stringResource(R.string.weather_triggers),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            fontFamily = PlusJakartaSansFontFamily,
            color = Black100
        )
    }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        TriggerCard(
            icon = R.drawable.ic_rainy,
            iconColor = ColdPrimary,
            title = stringResource(R.string.heavy_rain),
            subtitle = stringResource(R.string.heavy_rain_subtitle),
            isChecked = selectedFilter == WeatherFilter.RAIN,
            color = color,
            onCheckedChange = { onToggle(WeatherFilter.RAIN, it) }
        )
        TriggerCard(
            icon = R.drawable.ic_wind,
            iconColor = MildPrimary,
            title = stringResource(R.string.strong_wind),
            subtitle = stringResource(R.string.strong_wind_subtitle),
            isChecked = selectedFilter == WeatherFilter.WIND,
            color = color,
            onCheckedChange = { onToggle(WeatherFilter.WIND, it) }
        )
        TriggerCard(
            icon = R.drawable.ic_snowy,
            iconColor = HotPrimary,
            title = stringResource(R.string.snowfall),
            subtitle = stringResource(R.string.snowfall_subtitle),
            isChecked = selectedFilter == WeatherFilter.SNOW,
            color = color,
            onCheckedChange = { onToggle(WeatherFilter.SNOW, it) }
        )
        TriggerCard(
            icon = R.drawable.ic_thunderstorm,
            iconColor = HotSecondary,
            title = stringResource(R.string.thunderstorm),
            subtitle = stringResource(R.string.thunderstorm_subtitle),
            isChecked = selectedFilter == WeatherFilter.THUNDERSTORM,
            color = color,
            onCheckedChange = { onToggle(WeatherFilter.THUNDERSTORM, it) }
        )
    }
}