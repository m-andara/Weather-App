package com.example.weatherapp

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.weatherapp.networking.WeatherNetworking
import com.example.weatherapp.repository.WeatherRepository
import com.example.weatherapp.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val LOCATION_REQUEST_CODE = 756
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        WeatherNetworking.requestLocationPermission(
            this,
            binding.root.context
        ) {
            setWeatherData()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            LOCATION_REQUEST_CODE -> {
                if(
                    grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED
                ) {
                    setWeatherData()
                }
            }
        }
    }

    fun setWeatherData() {
        try {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    WeatherRepository.setCoords(
                        location.latitude,
                        location.longitude
                    )
                    WeatherNetworking.getUserCity(this, location.latitude,location.longitude) {

                    }
                }
        } catch(e: SecurityException) {
            Log.v("Location permissions", "There was a problem with location services permissions")
        }

    }
}