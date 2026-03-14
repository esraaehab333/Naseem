package com.example.naseem.presentation.fav.view

import android.content.Context.MODE_PRIVATE
import android.location.Address
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.naseem.R
import com.example.naseem.data.model.FavoriteModel
import com.example.naseem.presentation.fav.components.BottomSheetSection
import com.example.naseem.presentation.fav.components.LocationFloatingActionButton
import com.example.naseem.presentation.fav.components.MapSection
import com.example.naseem.presentation.fav.components.SearchSection
import com.example.naseem.presentation.fav.viewModels.FavoriteViewModel
import com.example.naseem.utils.LocationUtils
import com.google.android.gms.location.LocationServices
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint

@Composable
fun AddFavoritePlaceScreen(
    color: Color,
    viewModel: FavoriteViewModel,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val unknownLocation = stringResource(R.string.unknown_location)
    var searchQuery by remember { mutableStateOf("") }
    var suggestions by remember { mutableStateOf<List<Address>>(emptyList()) }
    var selectedLocation by remember { mutableStateOf<GeoPoint?>(null) }
    var selectedAddress by remember { mutableStateOf<Address?>(null) }
    var mapCenter by remember { mutableStateOf<GeoPoint?>(null) }
    var currentLocation by remember { mutableStateOf<GeoPoint?>(null) }
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val imeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
    val isSheetVisible = selectedLocation != null && !imeVisible

    LaunchedEffect(Unit) {
        Configuration.getInstance().apply {
            userAgentValue = context.packageName
            load(context, context.getSharedPreferences("osmdroid", MODE_PRIVATE))
        }
        LocationUtils.getCurrentLocation(context, fusedLocationClient) { point ->
            currentLocation = point
            mapCenter = point
        }
    }

    LaunchedEffect(selectedLocation) {
        selectedLocation?.let { location ->
            LocationUtils.getAddressFromLocation(context, location) { address ->
                selectedAddress = address
            }
        }
    }

    Scaffold(containerColor = Color.Transparent) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            MapSection(
                modifier = Modifier.fillMaxSize(),
                mapCenter = mapCenter,
                currentLocation = currentLocation,
                selectedLocation = selectedLocation,
                onLocationSelected = { point ->
                    selectedLocation = point
                    mapCenter = point
                }
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 20.dp, end = 20.dp)
            ) {
                SearchSection(
                    searchQuery = searchQuery,
                    suggestions = suggestions,
                    color = color,
                    onQueryChange = { query ->
                        searchQuery = query
                        if (query.length > 2) {
                            LocationUtils.searchAddress(context, query) { results ->
                                suggestions = results
                            }
                        } else {
                            suggestions = emptyList()
                        }
                    },
                    onSuggestionClick = { address ->
                        val point = GeoPoint(address.latitude, address.longitude)
                        selectedLocation = point
                        selectedAddress = address
                        mapCenter = point
                        searchQuery = ""
                        suggestions = emptyList()
                    }
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 16.dp, bottom = 12.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    LocationFloatingActionButton(
                        color = color,
                        onClick = {
                            LocationUtils.getCurrentLocation(context, fusedLocationClient) { point ->
                                currentLocation = point
                                selectedLocation = point
                                mapCenter = point
                            }
                        }
                    )
                }
                AnimatedVisibility(visible = isSheetVisible) {
                    BottomSheetSection(
                        color = color,
                        selectedAddress = selectedAddress,
                        selectedLocation = selectedLocation,
                        onSaveClick = {
                            val location = selectedLocation
                            if (location != null) {
                                viewModel.addToFavorites(
                                    FavoriteModel(
                                        cityName = selectedAddress?.locality ?: unknownLocation,
                                        fullAddress = selectedAddress?.getAddressLine(0)
                                            ?: "${location.latitude}, ${location.longitude}",
                                        latitude = location.latitude,
                                        longitude = location.longitude
                                    )
                                )
                                onNavigateBack()
                            }
                        }
                    )
                }
            }
        }
    }
}