package com.msingh.hospitalfinder.data.remote

import com.msingh.hospitalfinder.data.model.distance.LocationDistance
import com.msingh.hospitalfinder.data.model.places.GooglePlaceResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GooglePlaceApiService {
    @GET("place/nearbysearch/json")
    suspend fun searchNearbyPlaces(
        @Query("location") startLocation: String,
        @Query("location") destinationLocation: String,
        @Query("radius") radius: Int,
        @Query("type") type: String,
        @Query("key") apiKey: String
    ): GooglePlaceResponse


    @GET("distancematrix/json")
    suspend fun getDistanceBetweenLocation(
        @Query("origins") origins: String,
        @Query("destinations") destinations: String,
        @Query("key") apiKey: String
    ): LocationDistance
}