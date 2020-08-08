package com.example.weatherapp

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.example.weatherapp.Dialogs.PermissionsDialog

class Permissions() {

    companion object {
        fun requestLocationPermission(
            currentActivity: Activity,
            currentContext: Context,
            supportFragmentManager: FragmentManager
        ) {
            when {
                ContextCompat.checkSelfPermission(
                    currentContext,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED -> {
                    Toast.makeText(currentContext, "Hey you have permission!", Toast.LENGTH_LONG).show()
                }
                shouldShowRequestPermissionRationale(currentActivity, Manifest.permission.ACCESS_COARSE_LOCATION) -> {
                    val dialog = PermissionsDialog() {it ->
                        if(it == 1) {
                            requestPermissions(currentActivity, arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION), 756)
                        } else {

                        }
                    }

                    dialog.show(supportFragmentManager, "Location Permission Dialog")
                }
                else -> {
                    requestPermissions(currentActivity, arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION), 756)
                }
            }
        }
    }
}