package com.example.weatherapp.networking

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.weatherapp.repository.WeatherRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

object WeatherNetworking {

    val client = OkHttpClient()

    fun requestLocationPermission(
        currentActivity: Activity,
        currentContext: Context,
        onPermissionGranted: () -> Unit
    ) {
        when {
            ContextCompat.checkSelfPermission(
                currentContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(
                        currentContext,
                       Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED -> {
                onPermissionGranted()
            }
            else -> {
                ActivityCompat.requestPermissions(
                    currentActivity,
                    arrayOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ),
                    756
                )
            }
        }
    }

    fun getUserCity(context: Context, lat: Double, long: Double, onFinish: () -> Unit) {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses: List<Address> = geocoder.getFromLocation(lat, long, 1)
        if(addresses.isNotEmpty()) {
            WeatherRepository.setCity(addresses[0].locality)
            onFinish()
        }
    }

    private val weatherApi: WeatherApi
    get() {
        return Retrofit.Builder()
            .baseUrl(WeatherRepository.BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

    fun getWeather() {
        val coords = WeatherRepository.getCoords()
        weatherApi.getWeather(coords.first, coords.second, "")
    }
}