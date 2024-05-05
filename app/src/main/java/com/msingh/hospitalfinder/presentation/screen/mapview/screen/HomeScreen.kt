package com.msingh.hospitalfinder.presentation.screen.mapview.screen

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.Location
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.msingh.hospitalfinder.R
import com.msingh.hospitalfinder.presentation.screen.mapview.util.LocationUtil
import com.msingh.hospitalfinder.presentation.screen.mapview.viewmodel.MapViewModel
import com.msingh.hospitalfinder.util.BitmapImage.bitmapDescriptorFromVector
import com.msingh.hospitalfinder.util.Constant
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    onDataLoaded: () -> Unit,
    navigateToList: (startLocation: String, destinationLocation: String) -> Unit,
    viewModel: MapViewModel = hiltViewModel()
) {

    Scaffold {
        val activity = LocalContext.current as ComponentActivity
        val context = LocalContext.current
        var location by remember { mutableStateOf<Location?>(null) }
        val scope = rememberCoroutineScope()
        var currentLocation: LatLng = LatLng(1.35, 103.87)
        val cameraPositionState = rememberCameraPositionState {}
        val placesList by viewModel.placesList.collectAsState()
        var showShowRationalDialog by remember { mutableStateOf(false) }
        var permissionDennyDialog by remember { mutableStateOf(false) }

        val startLauncher =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    result.data?.let { data ->
                        val place = Autocomplete.getPlaceFromIntent(data)
//                    Log.i("Place: ${place.name}, ${place.id}")
                        Log.d(
                            "HomeScreen",
                            "HomeScreen() startLauncher called with: PLACE data = ${place.toString()}"
                        )
//                        startLocation.value = place.address?.toString() ?: ""
                        viewModel.updateStartLocationName(place.address?.toString() ?: "")
                        place.latLng?.let { it1 -> viewModel.updateStartLatLng(it1) }
                    }

                }
            }

        val destinationLauncher =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    result.data?.let { data ->
                        val place = Autocomplete.getPlaceFromIntent(data)
                        Log.d(
                            "HomeScreen",
                            "HomeScreen() destinationLauncher called with: PLACE data = ${place.toString()}"
                        )
                        place.latLng?.let { it1 -> viewModel.updateDestinationLatLng(it1) }
//                        destinationLocation.value = place.address?.toString() ?: ""
                        viewModel.updateDestinationLocationName(place.address?.toString() ?: "")
                    }

                }
            }




        LaunchedEffect(viewModel.startLocationName.value) {
            if (viewModel.startLatLng.value != null) {
                Log.d(
                    "HomeScreen",
                    "HomeScreen() called startLatLng : ${viewModel.startLatLng.value}"
                )
                cameraPositionState.position =
                    CameraPosition.fromLatLngZoom(viewModel.startLatLng.value!!, 10f)
            }
        }

        LaunchedEffect(viewModel.destinationLocationName.value) {
            if (viewModel.destinationLatLng.value != null) {
                Log.d(
                    "HomeScreen",
                    "HomeScreen() called destinationLatLng : ${viewModel.destinationLatLng.value}"
                )
                cameraPositionState.position =
                    CameraPosition.fromLatLngZoom(viewModel.destinationLatLng.value!!, 10f)
            }
        }

        LaunchedEffect(key1 = location) {
            Log.d("HomeScreen", "HomeScreen() called launchedd effect")
            currentLocation = LatLng(location?.latitude ?: 1.35, location?.longitude ?: 103.87)
            cameraPositionState.position = CameraPosition.fromLatLngZoom(currentLocation!!, 15f)
        }
        LaunchedEffect(Unit) {
            scope.launch {
                scope.launch {
                    location =
                        LocationUtil.getLastKnownLocation(activity) // Assuming you are using ViewModel and viewModelScope
                    Log.d("HomeScreen", "HomeScreen() called ${location.toString()}")
                }
            }
        }

        val locationPermissionLauncher =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { status ->
                if (status) {
                    scope.launch {
                        location =
                            LocationUtil.getLastKnownLocation(activity) // Assuming you are using ViewModel and viewModelScope
                        Log.d("HomeScreen", "HomeScreen() called ${location.toString()}")
                    }
                } else if (ActivityCompat.shouldShowRequestPermissionRationale(
                        activity,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                ) {
                    permissionDennyDialog = false
                    showShowRationalDialog = true;
                } else {
                    showShowRationalDialog = false
                    permissionDennyDialog = true
                }
            }

        Box {
            GoogleMap(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                cameraPositionState = cameraPositionState,
                contentPadding = it
            ) {

                if (viewModel.startLatLng.value != null) {
                    Marker(
                        state = MarkerState(position = viewModel.startLatLng.value!!),
                        title = "Start Location",
                        snippet = "This is start location",
                        icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                    )
                }

                if (viewModel.destinationLatLng.value != null) {
                    Marker(
                        state = MarkerState(position = viewModel.destinationLatLng.value!!),
                        title = "Destination Location",
                        snippet = "This is destination location",
                        icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)
                    )
                }

                if (placesList?.results?.isNotEmpty() == true) {
                    cameraPositionState.position =
                        CameraPosition.fromLatLngZoom(viewModel.startLatLng.value!!, 15f)
                }

                placesList?.results?.forEach { place ->
                    val icon =
                        bitmapDescriptorFromVector(context, R.drawable.baseline_local_hospital_24)

                    Marker(

                        state = MarkerState(
                            position = LatLng(
                                place.geometry.location.lat,
                                place.geometry.location.lng
                            )
                        ),
                        title = place.name,
                        snippet = place.vicinity,
                        icon = icon,
                    )
                }
            }

            Column(
                modifier = Modifier
                    .padding(15.dp)
                    .fillMaxWidth()
                    .padding(it)
                    .clip(MaterialTheme.shapes.medium)
                    .background(Color.White)
                    .height(250.dp)
                    .padding(20.dp)
            ) {
                TextField(value = viewModel.startLocationName.value, onValueChange = { text ->
//                    startLocation.value = text
                    viewModel.updateStartLocationName(text)
                }, enabled = false, singleLine = true, modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        startLauncher.launch(initPlaceSearch(context))
                    }, placeholder = {
                    Text(text = "Start Location")
                })
                Spacer(modifier = Modifier.height(20.dp))
                TextField(
                    value = viewModel.destinationLocationName.value,
                    onValueChange = { text ->
                        viewModel.updateDestinationLocationName(text)
                    },
                    placeholder = {
                        Text(text = "Destination Location")
                    },
                    enabled = false,
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            destinationLauncher.launch(initPlaceSearch(context))
                        })
                Spacer(modifier = Modifier.height(20.dp))
                Row {
                    Button(onClick = {
                        navigateToList(
                            "${viewModel.startLatLng.value?.latitude},${viewModel.startLatLng.value?.longitude}",
                            "${viewModel.destinationLatLng.value?.latitude},${viewModel.destinationLatLng.value?.longitude}"
                        )
                    }) {
                        Text(text = "Search")
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Button(onClick = {
                        viewModel.searchNearbyPlaces(
//                            startLocation = "${startLatLng?.latitude},${startLatLng?.longitude}",
                            startLocation = "${viewModel.startLatLng.value?.latitude},${viewModel.startLatLng.value?.longitude}",
//                            destinationLocation = "${destinationLatLng?.latitude},${destinationLatLng?.longitude}",
                            destinationLocation = "${viewModel.destinationLatLng.value?.latitude},${viewModel.destinationLatLng.value?.longitude}",
                            type = "hospital",
                            radius = 10000,
                            apiKey = Constant.API_KEY
                        )

                    }) {
                        Text(text = "Show On Map")
                    }
                }

            }
        }

    }
}

fun initPlaceSearch(context: Context): Intent {


    Places.initialize(context, Constant.API_KEY)
    val fields = listOf(
        Place.Field.ID,
        Place.Field.NAME,
        Place.Field.LAT_LNG,
        Place.Field.ADDRESS,
        Place.Field.TYPES
    )
    val placePickerIntent = Autocomplete.IntentBuilder(
        AutocompleteActivityMode.OVERLAY, fields
    )
        .build(context)
    return placePickerIntent
}

@Composable
@Preview(showBackground = true)
fun PreviewHomeScreen() {
    HomeScreen(onDataLoaded = {}, navigateToList = { start, dest -> })
}