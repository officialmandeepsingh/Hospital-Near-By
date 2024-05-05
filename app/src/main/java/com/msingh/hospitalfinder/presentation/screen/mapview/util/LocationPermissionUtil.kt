package com.msingh.hospitalfinder.presentation.screen.mapview.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object LocationPermissionUtil {
    private const val LOCATION_PERMISSION_REQUEST_CODE = 1001

    fun requestLocationPermission(
        context: Context,
        activity: ComponentActivity,
        onPermissionResult: (Boolean) -> Unit,
        shouldShowRational: () -> Unit,
        onDennyPermission:()-> Unit
    ){




        /*val permission = Manifest.permission.ACCESS_FINE_LOCATION
        when {
            ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED -> {
                onPermissionResult(true) // Permission already granted
            }
            ActivityCompat.shouldShowRequestPermissionRationale(activity, permission) -> {
                shouldShowRational()
//                ActivityCompat.requestPermissions(
//                    activity,
//                    arrayOf(permission),
//                    LOCATION_PERMISSION_REQUEST_CODE
//                )
            }
            else -> {
                *//*ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(permission),
                    LOCATION_PERMISSION_REQUEST_CODE
                )*//*
                onDennyPermission()
            }
        }*/


        /*val permission = Manifest.permission.ACCESS_FINE_LOCATION

        val requestPermissionLauncher =
            activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                onPermissionResult(isGranted)
            }

        if (ContextCompat.checkSelfPermission(
                context,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Permission already granted
            onPermissionResult(true)
        } else {
            // Permission not granted, request it
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                // Show rationale if necessary
                shouldShowRational()
            } else {
                // Request permission directly
                requestPermissionLauncher.launch(permission)
            }
        }*/
    }
}