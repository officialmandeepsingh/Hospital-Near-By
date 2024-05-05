package com.msingh.hospitalfinder.data.remote

import com.msingh.hospitalfinder.data.model.distance.LocationDistance
import com.msingh.hospitalfinder.data.model.places.GooglePlaceResponse
import retrofit2.http.Query
import javax.inject.Inject

class PlacesRepository @Inject constructor(
    private val placeApiService: GooglePlaceApiService
) {
    suspend fun searchNearbyPlaces(
        startLocation: String,
        destinationLocation: String,
        radius: Int,
        type: String,
        apiKey: String
    ): GooglePlaceResponse {
        return placeApiService.searchNearbyPlaces(
            startLocation, destinationLocation, radius, type, apiKey
        )
    }

    suspend fun getDistanceBetweenLocation(
        origins:String,
        destinations: String,
        apiKey: String
    ): LocationDistance{
        return placeApiService.getDistanceBetweenLocation(origins, destinations, apiKey)
    }
}