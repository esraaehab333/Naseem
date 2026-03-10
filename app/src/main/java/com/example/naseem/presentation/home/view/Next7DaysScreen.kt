import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.naseem.R
import com.example.naseem.presentation.home.componets.DailyForecastRow
import com.example.naseem.presentation.home.componets.ForecastDay
import com.example.naseem.presentation.home.componets.TemperatureTrendSection
import com.example.naseem.ui.theme.Black100
import com.example.naseem.ui.theme.PlusJakartaSansFontFamily
import com.example.naseem.ui.theme.White100

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Next7DaysScreen(color: Color , onBackButtonClick: () -> Unit) {
    val weeklyData = listOf(
        ForecastDay("Monday", "Sunny", 72, 60, Icons.Default.Star),
        ForecastDay("Tuesday", "Partly Cloudy", 68, 58, Icons.Default.Star),
        ForecastDay("Wednesday", "Light Rain", 64, 55, Icons.Default.Star),
        ForecastDay("Thursday", "Overcast", 62, 52, Icons.Default.Star),
        ForecastDay("Friday", "Sunny", 74, 59, Icons.Default.Star),
        ForecastDay("Saturday", "Sunny", 75, 61, Icons.Default.Star),
        ForecastDay("Sunday", "Cloudy", 70, 58, Icons.Default.Star)
    )
    val highTemps = weeklyData.map { it.high }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("7-Day Forecast", fontWeight = FontWeight.Bold, fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = onBackButtonClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(White100),
            contentPadding = innerPadding // مرري الـ object نفسه مباشرة هنا
        ) {
            item {
                TemperatureTrendSection(highTemps = highTemps, color = color)
            }
            item {
                Text(
                    text = "Daily Forecast",
                    modifier = Modifier.padding(20.dp),
                    color = Black100,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    fontFamily = PlusJakartaSansFontFamily
                )
            }
            items(weeklyData) { day ->
                DailyForecastRow(day, color = color , icon = R.drawable.ic_pressure)
            }
        }
    }
}