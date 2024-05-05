package com.msingh.hospitalfinder.presentation.screen.mapview.util

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.tasks.await


object LocationUtil {
    @SuppressLint("MissingPermission")
    suspend fun getLastKnownLocation(context: Context): Location?{
        val fusedLocationClient: FusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(context)
        return try {
            val location = fusedLocationClient.lastLocation.await()
            Log.d("location", "getLastKnownLocation() called with: context = $location")
            location
        } catch (exception: Exception) {
            null
        }
    }
}