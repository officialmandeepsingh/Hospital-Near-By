package com.msingh.hospitalfinder.presentation.screen.result.screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.libraries.places.api.model.Place
import com.msingh.hospitalfinder.data.model.places.Result
import com.msingh.hospitalfinder.presentation.screen.mapview.viewmodel.MapViewModel
import com.msingh.hospitalfinder.presentation.screen.result.components.HospitalListItem
import com.msingh.hospitalfinder.util.Constant
import kotlin.reflect.KProperty



@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HospitalList(onBackPressed: () -> Unit, viewModel: MapViewModel = hiltViewModel()) {


    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = { Text("Hospital List") }, navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back Arrow Icon"
                )
            }
        })
    }) {
        val placesList by viewModel.placesList.collectAsState()


        LazyColumn(modifier = Modifier.padding(it)) {
            Log.d("List", "HospitalList() called")
            placesList?.results?.let { places ->
                items(places) { place ->
                    var distance by remember {
                        mutableStateOf<String>("")
                    }

                    Log.d("List", "HospitalList() called With Result::: ${place.name}")
                        HospitalListItem(place = place, distance = distance.toString())
                    viewModel.getDistanceBetweenLocation("${viewModel.startLocation.value}", destinations = "${place.geometry.location.lat},${place.geometry.location.lng}", apiKey = Constant.API_KEY){
                        Log.d("List", "HospitalList() called: DISTANCE FOUND: $it")
                            distance = it
                    }
                }
            }


        }
    }
}