package com.msingh.hospitalfinder.presentation.screen.result.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.msingh.hospitalfinder.navigation.Screen
import com.msingh.hospitalfinder.presentation.screen.result.screen.HospitalList

fun NavGraphBuilder.hospitalListRoute(onBackPressed: () -> Unit) {
    composable(route = Screen.HospitalList.route) {
        HospitalList(onBackPressed = onBackPressed)
    }
}