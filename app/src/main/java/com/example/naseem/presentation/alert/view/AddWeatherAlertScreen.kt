package com.example.naseem.presentation.alert.view

import WeatherTriggerSection
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.naseem.R
import com.example.naseem.data.model.WeatherAlertModel
import com.example.naseem.presentation.alert.components.AlertTypeSection
import com.example.naseem.presentation.alert.components.ScheduleAndDurationSection
import com.example.naseem.presentation.alert.viewModel.WeatherAlertViewModel
import com.example.naseem.ui.theme.PlusJakartaSansFontFamily
import com.example.naseem.ui.theme.White100
import com.example.naseem.utils.WeatherFilter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddWeatherAlertScreen(
    color: Color,
    viewModel: WeatherAlertViewModel,
    onBackButtonClick: () -> Unit
) {
    var dateMillis by remember { mutableLongStateOf(System.currentTimeMillis()) }
    var fromMillis by remember { mutableLongStateOf(System.currentTimeMillis()) }
    var toMillis by remember { mutableLongStateOf(System.currentTimeMillis()) }
    var dateLabel by remember { mutableStateOf("") }
    var fromLabel by remember { mutableStateOf("08:00 AM") }
    var toLabel by remember { mutableStateOf("06:00 PM") }
    var alertType by remember { mutableStateOf("notification") }
    var selectedFilter by remember { mutableStateOf<WeatherFilter?>(null) }

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
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(padding)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(28.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            ScheduleAndDurationSection(
                color = color,
                onDateMillisChanged = { dateMillis = it },
                onFromMillisChanged = { fromMillis = it },
                onToMillisChanged   = { toMillis = it },
                onDateLabelChanged  = { dateLabel = it },
                onFromLabelChanged  = { fromLabel = it },
                onToLabelChanged    = { toLabel = it },
            )

            AlertTypeSection(
                color = color,
                selectedType = alertType,
                onTypeSelected = { alertType = it }
            )

            WeatherTriggerSection(
                color = color,
                onFilterSelected = { selectedFilter = it }
            )

            Button(
                onClick = {
                    selectedFilter?.let { filter ->
                        viewModel.addAlert(
                            WeatherAlertModel(
                                fromTimeMillis = fromMillis,
                                toTimeMillis   = toMillis,
                                dateLabel      = dateLabel,
                                fromLabel      = fromLabel,
                                toLabel        = toLabel,
                                alertType      = alertType,
                                weatherFilter  = filter,
                                latitude       = 0.0,
                                longitude      = 0.0,
                            )
                        )
                        onBackButtonClick()
                    }
                },
                enabled = selectedFilter != null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = color,
                    disabledContainerColor = color.copy(alpha = 0.4f)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = if (selectedFilter == null) "Select a weather trigger"
                    else "Save Alert",
                    color = White100,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = PlusJakartaSansFontFamily,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}