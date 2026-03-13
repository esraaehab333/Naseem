package com.example.naseem.presentation.fav.components

import android.location.Address
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.osmdroid.util.GeoPoint

class AddFavoriteState {
    var searchQuery by mutableStateOf("")
    var suggestions by mutableStateOf<List<Address>>(emptyList())
    var selectedLocation by mutableStateOf<GeoPoint?>(null)
    var selectedAddress by mutableStateOf<Address?>(null)
    var mapCenter by mutableStateOf<GeoPoint?>(null)
}