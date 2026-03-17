package com.example.naseem

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.LocationServices

class LocationHelper(private val context: Context) {

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(onResult: (Double, Double) -> Unit) {
        val fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(context)

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            location?.let {
                onResult(it.latitude, it.longitude)
            }
        }
    }
}