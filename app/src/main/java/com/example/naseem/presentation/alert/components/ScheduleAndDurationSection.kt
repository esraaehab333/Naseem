package com.example.naseem.presentation.alert.components
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SelectableDates
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.naseem.R
import com.example.naseem.ui.theme.Black100
import com.example.naseem.ui.theme.Gray100
import com.example.naseem.ui.theme.PlusJakartaSansFontFamily
import com.example.naseem.ui.theme.White100
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleAndDurationSection(
    color: Color,
    onDateMillisChanged: (Long) -> Unit,
    onFromMillisChanged: (Long) -> Unit,
    onToMillisChanged: (Long) -> Unit,
    onDateLabelChanged: (String) -> Unit,
    onFromLabelChanged: (String) -> Unit,
    onToLabelChanged: (String) -> Unit,
) {
    val dateFormat = remember { SimpleDateFormat("dd MMM, yyyy", Locale.getDefault()) }
    val todayMillis = remember {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        calendar.apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
    }
    val todayLabel = remember { dateFormat.format(Date(todayMillis)) }
    var showDatePicker by remember { mutableStateOf(false) }
    var showFromTimePicker by remember { mutableStateOf(false) }
    var showToTimePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf(todayLabel) }
    var fromTime by remember { mutableStateOf("08:00") }
    var fromAmPm by remember { mutableStateOf("AM") }
    var toTime by remember { mutableStateOf("08:00") }
    var toAmPm by remember { mutableStateOf("PM") }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = todayMillis,
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long) = utcTimeMillis >= todayMillis
        }
    )
    val fromTimeState = rememberTimePickerState(initialHour = 8, initialMinute = 0)
    val toTimeState   = rememberTimePickerState(initialHour = 18, initialMinute = 0)
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
                text = stringResource(R.string.schedule_and_duration),
                fontFamily = PlusJakartaSansFontFamily,
                fontWeight = FontWeight.Bold,
                color = Black100,
                fontSize = 14.sp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        PickerField(
            label = stringResource(R.string.label_date),
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
                label = stringResource(R.string.label_from),
                time = fromTime,
                amPm = fromAmPm,
                modifier = Modifier.weight(1f),
                color = color,
                onClick = { showFromTimePicker = true }
            )
            TimeBox(
                label = stringResource(R.string.label_to),
                time = toTime,
                amPm = toAmPm,
                modifier = Modifier.weight(1f),
                color = color,
                onClick = { showToTimePicker = true }
            )
        }
    }
    if (showDatePicker) {
        DatePickerDialogCustom(
            onDismissRequest = { showDatePicker = false },
            color = color,
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val label = dateFormat.format(Date(millis))
                        selectedDate = label
                        onDateMillisChanged(millis)
                        onDateLabelChanged(label)
                    }
                    showDatePicker = false
                }) { Text(stringResource(R.string.ok), color = color) }
            }
        ) {
            DatePicker(
                state = datePickerState,
                modifier = Modifier.padding(horizontal = 0.dp),
                showModeToggle = false,
                colors = DatePickerDefaults.colors(
                    containerColor = White100,
                    titleContentColor = color,
                    headlineContentColor = color,
                    weekdayContentColor = Gray100,
                    navigationContentColor = color,
                    yearContentColor = Black100,
                    currentYearContentColor = color,
                    selectedYearContentColor = White100,
                    selectedYearContainerColor = color,
                    dayContentColor = Black100,
                    disabledDayContentColor = Gray100,
                    selectedDayContentColor = White100,
                    selectedDayContainerColor = color,
                    todayContentColor = color,
                    todayDateBorderColor = color,
                )
            )
        }
    }
    if (showFromTimePicker) {
        TimePickerDialogCustom(
            onDismissRequest = { showFromTimePicker = false },
            color = color,
            confirmButton = {
                TextButton(onClick = {
                    val (time, amPm, millis) = resolveTime(fromTimeState.hour, fromTimeState.minute)
                    fromTime = time
                    fromAmPm = amPm
                    onFromMillisChanged(millis)
                    onFromLabelChanged("$time $amPm")
                    showFromTimePicker = false
                }) { Text(stringResource(R.string.ok), color = color) }
            }
        ) {
            TimePicker(
                state = fromTimeState,
                colors = TimePickerDefaults.colors(
                    clockDialColor = color.copy(alpha = 0.08f),
                    clockDialSelectedContentColor = White100,
                    clockDialUnselectedContentColor = Black100,
                    selectorColor = color,
                    containerColor = White100,
                    periodSelectorBorderColor = color.copy(alpha = 0.3f),
                    periodSelectorSelectedContainerColor = color,
                    periodSelectorUnselectedContainerColor = White100,
                    periodSelectorSelectedContentColor = White100,
                    periodSelectorUnselectedContentColor = Gray100,
                    timeSelectorSelectedContainerColor = color.copy(alpha = 0.12f),
                    timeSelectorUnselectedContainerColor = White100,
                    timeSelectorSelectedContentColor = color,
                    timeSelectorUnselectedContentColor = Gray100,
                )
            )
        }
    }
    if (showToTimePicker) {
        TimePickerDialogCustom(
            onDismissRequest = { showToTimePicker = false },
            color = color,
            confirmButton = {
                TextButton(onClick = {
                    val (time, amPm, millis) = resolveTime(toTimeState.hour, toTimeState.minute)
                    toTime = time
                    toAmPm = amPm
                    onToMillisChanged(millis)
                    onToLabelChanged("$time $amPm")
                    showToTimePicker = false
                }) { Text(stringResource(R.string.ok), color = color) }
            }
        ) {
            TimePicker(
                state = toTimeState,
                colors = TimePickerDefaults.colors(
                    clockDialColor = color.copy(alpha = 0.08f),
                    clockDialSelectedContentColor = White100,
                    clockDialUnselectedContentColor = Black100,
                    selectorColor = color,
                    containerColor = White100,
                    periodSelectorBorderColor = color.copy(alpha = 0.3f),
                    periodSelectorSelectedContainerColor = color,
                    periodSelectorUnselectedContainerColor = White100,
                    periodSelectorSelectedContentColor = White100,
                    periodSelectorUnselectedContentColor = Gray100,
                    timeSelectorSelectedContainerColor = color.copy(alpha = 0.12f),
                    timeSelectorUnselectedContainerColor = White100,
                    timeSelectorSelectedContentColor = color,
                    timeSelectorUnselectedContentColor = Gray100,
                )
            )
        }
    }
}
private data class TimeResult(val time: String, val amPm: String, val millis: Long)

private fun resolveTime(hour: Int, minute: Int): TimeResult {
    val displayHour = if (hour % 12 == 0) 12 else hour % 12
    val time = String.format("%02d:%02d", displayHour, minute)
    val amPm = if (hour < 12) "AM" else "PM"
    val millis = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, hour)
        set(Calendar.MINUTE, minute)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.timeInMillis
    return TimeResult(time, amPm, millis)
}