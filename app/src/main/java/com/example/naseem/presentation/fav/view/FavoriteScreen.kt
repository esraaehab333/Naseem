import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.naseem.R
import com.example.naseem.common.ApiState
import com.example.naseem.presentation.fav.components.WeatherCard
import com.example.naseem.presentation.fav.viewModels.FavoriteViewModel
import com.example.naseem.ui.theme.Black100
import com.example.naseem.ui.theme.Gray100
import com.example.naseem.ui.theme.PlusJakartaSansFontFamily
import com.example.naseem.ui.theme.White100

@Composable
fun FavoriteScreen(
    color: Color,
    viewModel: FavoriteViewModel,
    onFloatingActionButtonClicked: () -> Unit
) {
    val state by viewModel.favoritesState.collectAsState()
    Scaffold(
        containerColor = Color.Transparent,
        floatingActionButton = {
            FloatingActionButton(
                onClick = onFloatingActionButtonClicked,
                containerColor = color,
                contentColor = White100,
                shape = RoundedCornerShape(50.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_add),
                    tint = White100 ,
                    modifier = Modifier.size(26.dp),
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
                color = Gray100,
                fontFamily = PlusJakartaSansFontFamily,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            when (state) {
                //TODO: maybe changed later
                is ApiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = color)
                    }
                }
                is ApiState.Success -> {
                    val places = (state as ApiState.Success).data
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(bottom = 80.dp)
                    ) {
                        items(places) { place ->
                            WeatherCard(
                                place = place,
                                color = color,
                                onDeleteClick = {
                                    viewModel.deleteFromFavorites(place)
                                }
                            )
                        }
                    }
                }
                //TODO:Not handled yet
                is ApiState.Failure -> {
                    Text("Something went wrong", color = Color.Red)
                }
            }
        }
    }
}