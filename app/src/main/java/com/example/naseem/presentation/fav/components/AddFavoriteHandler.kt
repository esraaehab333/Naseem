package com.example.naseem.presentation.fav.components

import android.content.Context
import com.example.naseem.presentation.fav.components.FavoriteModel
import com.example.naseem.presentation.fav.viewModels.FavoriteViewModel
import com.example.naseem.utils.LocationUtils
import com.google.android.gms.location.FusedLocationProviderClient
import org.osmdroid.util.GeoPoint

class AddFavoriteHandler(
    private val context: Context,
    private val state: AddFavoriteState,
    private val viewModel: FavoriteViewModel,
    private val fusedLocationClient: FusedLocationProviderClient
) {
    // جيب الـ current location
    fun fetchCurrentLocation() {
        LocationUtils.getCurrentLocation(context, fusedLocationClient) { point ->
            state.mapCenter = point
        }
    }

    // لما يختار suggestion من الـ search
    fun onSuggestionSelected(address: android.location.Address) {
        val point = GeoPoint(address.latitude, address.longitude)
        state.selectedLocation = point
        state.selectedAddress = address
        state.mapCenter = point
        state.searchQuery = ""
        state.suggestions = emptyList()
    }

    // لما يدوس على زرار الـ current location
    fun onGetCurrentLocation() {
        LocationUtils.getCurrentLocation(context, fusedLocationClient) { point ->
            state.selectedLocation = point
            state.mapCenter = point
        }
    }

    // لما يدوس Save
    fun onSaveClick(
        onNoLocationSelected: () -> Unit
    ) {
        val location = state.selectedLocation
        val address = state.selectedAddress

        if (location != null) {
            val place = FavoriteModel(
                cityName = address?.locality ?: "Unknown Location",
                fullAddress = address?.getAddressLine(0)
                    ?: "${location.latitude}, ${location.longitude}",
                latitude = location.latitude,
                longitude = location.longitude
            )
            viewModel.addToFavorites(place)
        } else {
            onNoLocationSelected()
        }
    }

    // لما يكتب في الـ search
    fun onSearchQueryChanged(query: String) {
        state.searchQuery = query
        if (query.length > 2) {
            LocationUtils.searchAddress(context, query) { results ->
                state.suggestions = results
            }
        } else {
            state.suggestions = emptyList()
        }
    }

    // Reverse Geocoding لما يتغير الـ selectedLocation
    fun onLocationChanged(point: org.osmdroid.util.GeoPoint) {
        LocationUtils.getAddressFromLocation(context, point) { address ->
            state.selectedAddress = address
        }
    }
}