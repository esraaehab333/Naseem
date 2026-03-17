import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.naseem.R
import com.example.naseem.presentation.home.componets.DailyForecastRow
import com.example.naseem.utils.ForecastDay
import com.example.naseem.presentation.home.componets.TemperatureTrendSection
import com.example.naseem.presentation.home.viewModels.HomeViewModel
import com.example.naseem.ui.theme.PlusJakartaSansFontFamily
import com.example.naseem.ui.theme.White100
import com.example.naseem.utils.formatUnixToDay
import com.example.naseem.utils.getWeatherIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Next7DaysScreen(color: Color, viewModel: HomeViewModel, onBackButtonClick: () -> Unit) {
    val forecastData by viewModel.forecastData.collectAsState()
    val dailyList = forecastData?.list?.filter {
        it.dtTxt?.contains("12:00:00") == true
    } ?: emptyList()
    val highTemps = dailyList.map { it.main?.tempMax?.toInt() ?: 0 }
    val lowTemps = dailyList.map { it.main?.tempMin?.toInt() ?: 0 }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(R.string.five_day_forecast),
                        fontWeight = FontWeight.Bold,
                        fontFamily = PlusJakartaSansFontFamily,
                        fontSize = 18.sp)},
                navigationIcon = {
                    IconButton(onClick = onBackButtonClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.back))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(White100)
        ) {
            item {
                if (highTemps.isNotEmpty()) {
                    TemperatureTrendSection(
                        highTemps = highTemps,
                        lowTemps= lowTemps,
                        color = color
                    )
                }
            }
            item {
                Text(
                    stringResource(R.string.daily_forecast),
                    modifier = Modifier.padding(20.dp),
                    fontWeight = FontWeight.Bold,
                    fontFamily = PlusJakartaSansFontFamily
                )
            }
            items(dailyList) { day ->
                val weatherInfo = day.weather?.firstOrNull()
                val iconRes = getWeatherIcon(weatherInfo?.icon)
                val highTemp = day.main?.tempMax?.toInt() ?: 0
                val lowTemp = day.main?.tempMin?.toInt() ?: 0

                DailyForecastRow(
                    data = ForecastDay(
                        dayName = formatUnixToDay(day.dt ?: 0L),
                        high = highTemp,
                        low = lowTemp,
                        color = color,
                        status = weatherInfo?.main ?: stringResource(R.string.unknown_status),
                    ),
                    color = color,
                    icon = iconRes
                )
            }
        }
    }
}