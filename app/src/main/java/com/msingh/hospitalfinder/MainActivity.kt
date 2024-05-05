package com.msingh.hospitalfinder

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.msingh.hospitalfinder.navigation.Screen
import com.msingh.hospitalfinder.navigation.SetupNavGraph
import com.msingh.hospitalfinder.presentation.component.DisplayAlertDialog
import com.msingh.hospitalfinder.presentation.screen.mapview.viewmodel.MapViewModel
import com.msingh.hospitalfinder.ui.theme.HospitalFinderTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var keepSplashOpened = true
    private val sharedViewModel: MapViewModel by viewModels()

    val locationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { status ->
            if (status) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                getLocation()
                keepSplashOpened = false
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                showPermissionRequiredDialog()
                Toast.makeText(this, "Permission required", Toast.LENGTH_SHORT).show()
            } else {
                showPermissionRequiredDialog()
                Toast.makeText(this, "Permission denny by user", Toast.LENGTH_SHORT).show()
            }
        }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition {
            keepSplashOpened
        }
        if (ActivityCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ){
            keepSplashOpened = false
            getLocation()
        }
        else
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        // Remove status bar
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            HospitalFinderTheme {
                val navController = rememberNavController()
                SetupNavGraph(
                    startDestination = Screen.HomeScreen.route,
                    navController = navController,
                    onDataLoaded = {
                        Log.d("onDataLoaded", "onCreate() called")
//                        keepSplashOpened = false
                    }
                )


            }
        }
    }
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private fun getLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    val latitude = location.latitude
                    val longitude = location.longitude
                    // Do something with the latitude and longitude
                } else {
                    // Handle the case where location is null
                    Toast.makeText(this, "Location not available", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                // Handle any errors
                Toast.makeText(this, "Error getting location: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun showPermissionRequiredDialog() {
        keepSplashOpened= false
        AlertDialog.Builder(this)
            .setTitle("Permission Required")
            .setMessage("Please grant location permission to continue")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
            .show()
    }



}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HospitalFinderTheme {
        val navController = rememberNavController()
        SetupNavGraph(
            startDestination = Screen.HomeScreen.route,
            navController = navController,
            onDataLoaded = {

            }
        )
    }
}