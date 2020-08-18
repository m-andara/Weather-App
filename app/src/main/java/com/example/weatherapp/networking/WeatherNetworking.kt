package com.example.weatherapp.networking

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.weatherapp.R
import com.example.weatherapp.models.Weather
import com.example.weatherapp.models.DailyWeather
import com.example.weatherapp.models.WeatherTypeImage
import com.example.weatherapp.repository.WeatherRepository
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

object WeatherNetworking {

    private val client = OkHttpClient()

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
        }
        onFinish()
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

     fun getWeather(context: Context, onFinished: () -> Unit) {
        val coords = WeatherRepository.getCoords()
         val res = context.resources
        weatherApi.getWeather(coords.first, coords.second, res.getString(R.string.weather_key)).enqueue(object:
            Callback<CurrentWeatherItem> {
            override fun onFailure(call: Call<CurrentWeatherItem>, t: Throwable) {
                Log.v("Networking", "Error: $t")
            }

            override fun onResponse(
                call: Call<CurrentWeatherItem>,
                response: Response<CurrentWeatherItem>
            ) {
                val weather = response.body()?.currentWeather?.toModel()
                val daily = response.body()?.daily?.map { it ->
                    it.toModel()
                } ?: emptyList()
                val hourly = response.body()?.hourly?.map { it ->
                    it.toModel()
                } ?: emptyList()
                if (weather != null) {
                    WeatherRepository.setCurrentWeather(weather)
                }
                WeatherRepository.setDailyWeather(daily.subList(0, 5))
                WeatherRepository.setHourlyWeather(hourly.subList(0, 5))
                onFinished()
            }

        })
    }

    private fun CurrentWeatherItems.toModel(): Weather {
        return Weather(
            temp = temp,
            humidity = humidity,
            feelsLikeTemp = feelsLikeTemp,
            weatherType = WeatherTypeImage.of(weather[0].weatherType),
            weatherDesc = weather[0].description
        )
    }

    private fun DailyWeatherItem.toModel(): DailyWeather {
        return DailyWeather(
            dayTemp = temp.dayTemp,
            nightTemp = temp.nightTemp,
            dayLike = feelsLike.dayLike,
            nightLike = feelsLike.nightLike,
            humidity = humidity,
            weatherType = WeatherTypeImage.of(weather[0].weatherType),
            weatherDesc = weather[0].description
        )
    }
}