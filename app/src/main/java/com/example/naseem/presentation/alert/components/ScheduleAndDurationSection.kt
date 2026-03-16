package com.example.naseem.presentation.alert.components

import TimeBox
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.naseem.R
import com.example.naseem.ui.theme.Black100
import com.example.naseem.ui.theme.PlusJakartaSansFontFamily
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleAndDurationSection(color: Color) {
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePickerByFrom by remember { mutableStateOf(false) }
    var showTimePickerByTo by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf("24 Oct, 2023") }
    var fromTime by remember { mutableStateOf("08:00") }
    var fromAmPm by remember { mutableStateOf("AM") }
    var toTime by remember { mutableStateOf("06:00") }
    var toAmPm by remember { mutableStateOf("PM") }

    val datePickerState = rememberDatePickerState()
    val fromTimeState = rememberTimePickerState(initialHour = 8, initialMinute = 0)
    val toTimeState = rememberTimePickerState(initialHour = 18, initialMinute = 0)

    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(R.drawable.ic_schedule),
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Schedule and Duration",
                fontFamily = PlusJakartaSansFontFamily,
                fontWeight = FontWeight.Bold,
                color = Black100,
                fontSize = 14.sp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        PickerField(
            label = "DATE",
            value = selectedDate,
            modifier = Modifier.fillMaxWidth(),
            onClick = { showDatePicker = true }
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TimeBox(
                label = "FROM",
                time = fromTime,
                amPm = fromAmPm,
                modifier = Modifier.weight(1f),
                color = color,
                onClick = { showTimePickerByFrom = true }
            )
            TimeBox(
                label = "TO",
                time = toTime,
                amPm = toAmPm,
                modifier = Modifier.weight(1f),
                color = color,
                onClick = { showTimePickerByTo = true }
            )
        }
    }
    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let {
                        selectedDate = SimpleDateFormat("dd MMM, yyyy", Locale.getDefault())
                            .format(Date(it))
                    }
                    showDatePicker = false
                }) { Text("OK", color = color) }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancel", color = color.copy(alpha = 0.6f))
                }
            },
            shape = RoundedCornerShape(28.dp),
            colors = DatePickerDefaults.colors(
                containerColor = Color.White,
                titleContentColor = color,
                headlineContentColor = color,
                weekdayContentColor = Color(0xFF6B7280),
                navigationContentColor = color,
                yearContentColor = Color(0xFF111827),
                currentYearContentColor = color,
                selectedYearContentColor = Color.White,
                selectedYearContainerColor = color,
                dayContentColor = Color(0xFF111827),
                disabledDayContentColor = Color(0xFF9CA3AF),
                selectedDayContentColor = Color.White,
                selectedDayContainerColor = color,
                todayContentColor = color,
                todayDateBorderColor = color,
            )
        ) {
            DatePicker(state = datePickerState)
        }
    }
    if (showTimePickerByFrom) {
        TimePickerDialogCustom(
            onDismissRequest = { showTimePickerByFrom = false },
            color = color,
            confirmButton = {
                TextButton(onClick = {
                    val hour = if (fromTimeState.hour % 12 == 0) 12 else fromTimeState.hour % 12
                    fromTime = String.format("%02d:%02d", hour, fromTimeState.minute)
                    fromAmPm = if (fromTimeState.hour < 12) "AM" else "PM"
                    showTimePickerByFrom = false
                }) { Text("OK", color = color) }
            }
        ) {
            TimePicker(
                state = fromTimeState,
                colors = TimePickerDefaults.colors(
                    clockDialColor = color.copy(alpha = 0.08f),
                    clockDialSelectedContentColor = Color.White,
                    clockDialUnselectedContentColor = Color(0xFF111827),
                    selectorColor = color,
                    containerColor = Color.White,
                    periodSelectorBorderColor = color.copy(alpha = 0.3f),
                    periodSelectorSelectedContainerColor = color,
                    periodSelectorUnselectedContainerColor = Color(0xFFF9FAFB),
                    periodSelectorSelectedContentColor = Color.White,
                    periodSelectorUnselectedContentColor = Color(0xFF6B7280),
                    timeSelectorSelectedContainerColor = color.copy(alpha = 0.12f),
                    timeSelectorUnselectedContainerColor = Color(0xFFF3F4F6),
                    timeSelectorSelectedContentColor = color,
                    timeSelectorUnselectedContentColor = Color(0xFF6B7280),
                )
            )
        }
    }
    if (showTimePickerByTo) {
        TimePickerDialogCustom(
            onDismissRequest = { showTimePickerByTo = false },
            color = color,
            confirmButton = {
                TextButton(onClick = {
                    val hour = if (toTimeState.hour % 12 == 0) 12 else toTimeState.hour % 12
                    toTime = String.format("%02d:%02d", hour, toTimeState.minute)
                    toAmPm = if (toTimeState.hour < 12) "AM" else "PM"
                    showTimePickerByTo = false
                }) { Text("OK", color = color) }
            }
        ) {
            TimePicker(
                state = toTimeState,
                colors = TimePickerDefaults.colors(
                    clockDialColor = color.copy(alpha = 0.08f),
                    clockDialSelectedContentColor = Color.White,
                    clockDialUnselectedContentColor = Color(0xFF111827),
                    selectorColor = color,
                    containerColor = Color.White,
                    periodSelectorBorderColor = color.copy(alpha = 0.3f),
                    periodSelectorSelectedContainerColor = color,
                    periodSelectorUnselectedContainerColor = Color(0xFFF9FAFB),
                    periodSelectorSelectedContentColor = Color.White,
                    periodSelectorUnselectedContentColor = Color(0xFF6B7280),
                    timeSelectorSelectedContainerColor = color.copy(alpha = 0.12f),
                    timeSelectorUnselectedContainerColor = Color(0xFFF3F4F6),
                    timeSelectorSelectedContentColor = color,
                    timeSelectorUnselectedContentColor = Color(0xFF6B7280),
                )
            )
        }
    }
}