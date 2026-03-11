import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.naseem.R
import com.example.naseem.presentation.fav.components.SavedPlace
import com.example.naseem.presentation.fav.components.WeatherCard
import com.example.naseem.ui.theme.Black100
import com.example.naseem.ui.theme.Gray100
import com.example.naseem.ui.theme.PlusJakartaSansFontFamily
import com.example.naseem.ui.theme.White100

@Composable
fun FavoriteScreen(color: Color) {
    val savedPlaces = listOf(
        SavedPlace("San Francisco, CA", "68°F", "Partly Cloudy", R.drawable.ic_cloudy),
        SavedPlace("New York, NY", "52°F", "Light Rain", R.drawable.ic_rainy),
        SavedPlace("Miami, FL", "84°F", "Clear Sky", R.drawable.ic_clear),
        SavedPlace("London, UK", "48°F", "Overcast", R.drawable.ic_cloudy)
    )

    Scaffold(
        containerColor = Color.Transparent,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO: Add new place logic */ },
                containerColor = color,
                contentColor = White100,
                shape = RoundedCornerShape(50.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = "Add Place"
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Saved Places",
                fontFamily = PlusJakartaSansFontFamily,
                fontWeight = FontWeight.Bold,
                color = Black100,
                fontSize = 18.sp,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Text(
                text = "Quick access to your top destinations",
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = Gray100,
                fontFamily = PlusJakartaSansFontFamily,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                items(savedPlaces) { place ->
                    WeatherCard(place, color = color)
                }
            }
        }
    }
}