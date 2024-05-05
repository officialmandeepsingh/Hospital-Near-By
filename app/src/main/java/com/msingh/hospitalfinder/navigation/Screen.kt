package com.msingh.hospitalfinder.navigation

sealed class Screen(val route: String, val label: String){
    data object HomeScreen : Screen(route= "on_home_screen", label= "Home")
//    data object HospitalList : Screen(route= "on_hospital_list_screen?startLocation={location}&", label= "Hospital List"){
    data object HospitalList : Screen(route = "on_hospital_list_screen?startLocation={startLocation}&destinationLocation={destinationLocation}", label = "Hospital List"){
//        fun passLocation(location:String) = "on_hospital_list_screen?location=$location"
fun passLocations(startLocation: String, destinationLocation: String): String {
    return "on_hospital_list_screen?startLocation=$startLocation&destinationLocation=$destinationLocation"
}
    }
}
