package com.msingh.hospitalfinder.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.msingh.hospitalfinder.presentation.screen.mapview.navigation.homeRoute
import com.msingh.hospitalfinder.presentation.screen.result.navigation.hospitalListRoute

@Composable
fun SetupNavGraph(
    startDestination: String,
    navController: NavHostController,
    onDataLoaded: () -> Unit,
) {
    NavHost(navController = navController, startDestination = startDestination) {
        homeRoute(
            onDataLoaded= onDataLoaded,
           /* navigateToList =(startLocation: String,destinationLocation: String) {
                Log.d("NavGraph", "SetupNavGraph() called with: location = $location")
                navController.navigate(Screen.HospitalList.passLocations(location))
            }*/
            navigateToList = {start,destination ->
                Log.d("NavGraph", "SetupNavGraph() called with: location = $start and $destination")
                navController.navigate(Screen.HospitalList.passLocations(start,destination))
            }
        )

        hospitalListRoute(
            onBackPressed = {
                navController.popBackStack()
            }
        )

    }
}
