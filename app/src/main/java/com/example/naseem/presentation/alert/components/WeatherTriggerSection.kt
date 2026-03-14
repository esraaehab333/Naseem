import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
fun WeatherTriggerSection(color: Color){
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_weather_triggers),
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            "Weather Triggers",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            fontFamily = PlusJakartaSansFontFamily,
            color = Black100
        )
    }
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        TriggerCard(R.drawable.ic_rainy, ColdPrimary, "Heavy Rain", "Alert when > 5mm/h", true , color)
        TriggerCard(R.drawable.ic_wind, MildPrimary, "Strong Wind", "Alert when > 40km/h", true,color)
        TriggerCard(R.drawable.ic_snowy, HotPrimary, "Snowfall", "Alert for any snow", true,color)
        TriggerCard(R.drawable.ic_thunderstorm, HotSecondary, "Thunderstorm", "Detection in 50km radius", true,color)
    }
}