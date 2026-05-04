package com.example.naseem.presentation.settings.view

import SettingsMapBottomSheet
import android.content.Context.MODE_PRIVATE
import android.location.Address
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.example.naseem.presentation.fav.components.LocationFloatingActionButton
import com.example.naseem.presentation.fav.components.MapSection
import com.example.naseem.presentation.fav.components.NoNetworkDialog
import com.example.naseem.presentation.fav.components.SearchSection
import com.example.naseem.presentation.settings.viewModel.SettingsViewModel
import com.example.naseem.utils.LocationUtils
import com.example.naseem.utils.NetworkUtils
import com.google.android.gms.location.LocationServices
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint

@Composable
fun SettingsMapScreen(
    color: Color,
    settingsViewModel: SettingsViewModel,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    var searchQuery by remember { mutableStateOf("") }
    var suggestions by remember { mutableStateOf<List<Address>>(emptyList()) }
    var selectedLocation by remember { mutableStateOf<GeoPoint?>(null) }
    var selectedAddress by remember { mutableStateOf<Address?>(null) }
    var mapCenter by remember { mutableStateOf<GeoPoint?>(null) }
    var currentLocation by remember { mutableStateOf<GeoPoint?>(null) }
    var showNoNetworkDialog by remember { mutableStateOf(false) }
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val imeVisible   = WindowInsets.ime.getBottom(LocalDensity.current) > 0
    val isSheetVisible = selectedLocation != null && !imeVisible
    LaunchedEffect(Unit) {
        if (!NetworkUtils.isNetworkAvailable(context)) {
            showNoNetworkDialog = true
        } else {
            Configuration.getInstance().apply {
                userAgentValue = context.packageName
                load(context, context.getSharedPreferences("osmdroid", MODE_PRIVATE))
            }
            LocationUtils.getCurrentLocation(context, fusedLocationClient) { point ->
                currentLocation = point
                mapCenter = point
            }
        }
    }
    LaunchedEffect(selectedLocation) {
        selectedLocation?.let { loc ->
            if (NetworkUtils.isNetworkAvailable(context)) {
                LocationUtils.getAddressFromLocation(context, loc) { address ->
                    selectedAddress = address
                }
            } else {
                showNoNetworkDialog = true
            }
        }
    }
    if (showNoNetworkDialog) {
        NoNetworkDialog(
            color = color,
            onDismiss = {
                showNoNetworkDialog = false
                onNavigateBack()
            }
        )
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
                        selectedLocation= point
                        selectedAddress= address
                        mapCenter= point
                        searchQuery= ""
                        suggestions= emptyList()
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
                    SettingsMapBottomSheet(
                        color = color,
                        selectedAddress = selectedAddress,
                        selectedLocation = selectedLocation,
                        onSaveClick = {
                            val loc = selectedLocation
                            if (loc != null) {
                                val label = selectedAddress?.locality
                                    ?: selectedAddress?.getAddressLine(0)
                                    ?: "${loc.latitude}, ${loc.longitude}"

                                settingsViewModel.saveMapLocation(
                                    lat = loc.latitude,
                                    lon = loc.longitude,
                                    label = label
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