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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.naseem.R
import com.example.naseem.presentation.alert.components.TriggerCard
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

    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(R.drawable.ic_weather_triggers),
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            "Weather Triggers",
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
            title = "Heavy Rain",
            subtitle = "Alert when > 5mm/h",
            isChecked = selectedFilter == WeatherFilter.RAIN,
            color = color,
            onCheckedChange = {
                selectedFilter = if (it) WeatherFilter.RAIN else null
                onFilterSelected(selectedFilter)
            }
        )
        TriggerCard(
            icon = R.drawable.ic_wind,
            iconColor = MildPrimary,
            title = "Strong Wind",
            subtitle = "Alert when > 40km/h",
            isChecked = selectedFilter == WeatherFilter.WIND,
            color = color,
            onCheckedChange = {
                selectedFilter = if (it) WeatherFilter.WIND else null
                onFilterSelected(selectedFilter)
            }
        )
        TriggerCard(
            icon = R.drawable.ic_snowy,
            iconColor = HotPrimary,
            title = "Snowfall",
            subtitle = "Alert for any snow",
            isChecked = selectedFilter == WeatherFilter.SNOW,
            color = color,
            onCheckedChange = {
                selectedFilter = if (it) WeatherFilter.SNOW else null
                onFilterSelected(selectedFilter)
            }
        )
        TriggerCard(
            icon = R.drawable.ic_thunderstorm,
            iconColor = HotSecondary,
            title = "Thunderstorm",
            subtitle = "Detection in 50km radius",
            isChecked = selectedFilter == WeatherFilter.THUNDERSTORM,
            color = color,
            onCheckedChange = {
                selectedFilter = if (it) WeatherFilter.THUNDERSTORM else null
                onFilterSelected(selectedFilter)
            }
        )
    }
}