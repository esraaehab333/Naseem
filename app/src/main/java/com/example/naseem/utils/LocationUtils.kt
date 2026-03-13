package com.example.naseem.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import org.osmdroid.util.GeoPoint
import java.util.Locale

object LocationUtils {

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(
        context: Context,
        fusedLocationClient: FusedLocationProviderClient,
        onResult: (GeoPoint) -> Unit
    ) {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    onResult(GeoPoint(location.latitude, location.longitude))
                } else {
                    onResult(GeoPoint(30.0444, 31.2357)) // Cairo as fallback
                }
            }
            .addOnFailureListener {
                onResult(GeoPoint(30.0444, 31.2357))
            }
    }

    fun getAddressFromLocation(
        context: Context,
        location: GeoPoint,
        onResult: (Address?) -> Unit
    ) {
        try {
            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            onResult(addresses?.firstOrNull())
        } catch (e: Exception) {
            Log.e("LocationUtils", "Reverse Geocoding Failed: ${e.message}")
            onResult(null)
        }
    }

    fun searchAddress(
        context: Context,
        query: String,
        onResult: (List<Address>) -> Unit
    ) {
        try {
            val geocoder = Geocoder(context, Locale.getDefault())
            val results = geocoder.getFromLocationName(query, 3)
            onResult(results ?: emptyList())
        } catch (e: Exception) {
            onResult(emptyList())
        }
    }
}