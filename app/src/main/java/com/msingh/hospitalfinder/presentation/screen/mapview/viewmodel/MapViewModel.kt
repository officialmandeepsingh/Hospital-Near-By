package com.msingh.hospitalfinder.presentation.screen.mapview.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.msingh.hospitalfinder.data.model.places.GooglePlaceResponse
import com.msingh.hospitalfinder.data.remote.PlacesRepository
import com.msingh.hospitalfinder.util.Constant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val placesRepository: PlacesRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _placesList: MutableStateFlow<GooglePlaceResponse?> = MutableStateFlow(null)
    val placesList: StateFlow<GooglePlaceResponse?> = _placesList

    private var _startLocation = mutableStateOf<String?>("")
    val startLocation = _startLocation

    private var _destinationLocation = mutableStateOf<String?>("")
    val destinationLocation = _destinationLocation

    private var _startLatLng = mutableStateOf<LatLng?>(null)
    val startLatLng: State<LatLng?> = _startLatLng

    private var _destinationLatLng = mutableStateOf<LatLng?>(null)
    val destinationLatLng: State<LatLng?> = _destinationLatLng

    private var _startLocationName = mutableStateOf<String>("")
    val startLocationName = _startLocationName

    private var _destinationLocationName = mutableStateOf<String>("")
    val destinationLocationName = _destinationLocationName




    init {
        getIdArgument()
    }


    fun updateStartLatLng(latLng: LatLng){
        _startLatLng.value = latLng
    }
    fun updateDestinationLatLng(latLng: LatLng){
        _destinationLatLng.value = latLng
    }

    fun updateStartLocationName(name: String){
        _startLocationName.value = name
    }

    fun updateDestinationLocationName(name: String){
        _destinationLocationName.value = name
    }

    private fun getIdArgument() {

        _startLocation.value = savedStateHandle.get<String>(
            key = "startLocation"
        )

        _destinationLocation.value = savedStateHandle.get<String>(key = "destinationLocation")

        searchNearbyPlaces(
            startLocation = startLocation.value.toString(),
            destinationLocation = destinationLocation.value.toString(),
            type = "hospital",
            radius = 10000,
            apiKey = Constant.API_KEY
        )
        Log.d("Model", "getDiaryIdArgument() called ${startLocation.value}")
    }

    fun searchNearbyPlaces(
        startLocation: String,
        destinationLocation: String,
        radius: Int,
        type: String,
        apiKey: String
    ) {
        viewModelScope.launch {
            Log.d("API", "searchNearbyPlaces() called:")
//            val response = placesRepository.searchNearbyPlaces(location, radius, type, apiKey)
            val response = placesRepository.searchNearbyPlaces(
                startLocation,
                destinationLocation,
                radius,
                type,
                apiKey
            )
            Log.d("API", "searchNearbyPlaces() called URL: ${response.toString()}")
            _placesList.value = response
            Log.d("API", "searchNearbyPlaces() RESPONSE called: ${placesList.value?.results?.size}")
        }
    }

    fun getDistanceBetweenLocation(
        origins: String,
        destinations: String,
        apiKey: String,
        onSuccess: (distance: String) -> Unit,
    ) {
        viewModelScope.launch {
            val response =
                placesRepository.getDistanceBetweenLocation(origins, destinations, apiKey)
//            response.rows[0].elements[0].distance
            val distance = response.rows[0].elements[0].distance.text
            onSuccess(distance)

        }
    }
}