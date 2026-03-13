package com.example.naseem.presentation.fav.view

import ButtonRow
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.naseem.presentation.fav.components.AddFavoriteHandler
import com.example.naseem.presentation.fav.components.AddFavoriteState
import com.example.naseem.presentation.fav.components.MapSection
import com.example.naseem.presentation.fav.components.SearchSection
import com.example.naseem.presentation.fav.viewModels.FavoriteViewModel
import kotlinx.coroutines.launch
import org.osmdroid.config.Configuration

@Composable
fun AddFavoritePlaceScreen(
    color: Color,
    viewModel: FavoriteViewModel,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val state = remember { AddFavoriteState() }

    val fusedLocationClient = remember {
        com.google.android.gms.location.LocationServices.getFusedLocationProviderClient(context)
    }

    val handler = remember {
        AddFavoriteHandler(context, state, viewModel, fusedLocationClient)
    }

    // إعداد الـ Map و جيب الـ current location
    LaunchedEffect(Unit) {
        Configuration.getInstance().apply {
            userAgentValue = context.packageName
            load(context, context.getSharedPreferences("osmdroid", android.content.Context.MODE_PRIVATE))
        }
        handler.fetchCurrentLocation()
    }

    // لما الـ selectedLocation يتغير → Reverse Geocoding
    LaunchedEffect(state.selectedLocation) {
        state.selectedLocation?.let { handler.onLocationChanged(it) }
    }

    // مراقبة الـ addEvent
    LaunchedEffect(Unit) {
        viewModel.addEvent.collect { message ->
            snackbarHostState.showSnackbar(message)
            onNavigateBack()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        containerColor = Color.Transparent
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // الـ Map
            MapSection(
                modifier = Modifier.fillMaxSize(),
                mapCenter = state.mapCenter,
                selectedLocation = state.selectedLocation
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 60.dp, start = 20.dp, end = 20.dp)
            ) {
                // الـ Search
                SearchSection(
                    searchQuery = state.searchQuery,
                    suggestions = state.suggestions,
                    color = color,
                    onQueryChange = { handler.onSearchQueryChanged(it) },
                    onSuggestionClick = { handler.onSuggestionSelected(it) }
                )

                Spacer(modifier = Modifier.weight(1f))

                // الـ Buttons
                ButtonRow(
                    color = color,
                    onGetCurrentLocation = { handler.onGetCurrentLocation() },
                    onSaveClick = {
                        handler.onSaveClick(
                            onNoLocationSelected = {
                                scope.launch {
                                    snackbarHostState.showSnackbar("Please select a location first")
                                }
                            }
                        )
                    }
                )
            }
        }
    }
}