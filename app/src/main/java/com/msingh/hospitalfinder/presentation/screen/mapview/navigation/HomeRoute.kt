package com.msingh.hospitalfinder.presentation.screen.mapview.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.msingh.hospitalfinder.navigation.Screen
import com.msingh.hospitalfinder.presentation.screen.mapview.screen.HomeScreen
import com.msingh.hospitalfinder.presentation.screen.result.screen.HospitalList

fun NavGraphBuilder.homeRoute(
    onDataLoaded: () -> Unit,
    navigateToList: (startLocation: String,destinationLocation: String) -> Unit,
) {
    composable(route = Screen.HomeScreen.route) {
        HomeScreen(onDataLoaded = onDataLoaded, navigateToList = navigateToList)
    }
}