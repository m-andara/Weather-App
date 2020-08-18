package com.example.weatherapp

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.networking.WeatherNetworking
import com.example.weatherapp.repository.WeatherRepository
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.recyclerview.DailyWeatherListAdapter
import com.example.weatherapp.recyclerview.HourlyWeatherListAdapter
import com.example.weatherapp.utils.DateUtils
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val dailyWeatherAdapter = DailyWeatherListAdapter()
    private val hourlyWeatherAdapter = HourlyWeatherListAdapter()

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val locationRequestCode = 756
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
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
            locationRequestCode -> {
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

    private fun setWeatherData() {
        try {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    WeatherRepository.setCoords(
                        location.latitude,
                        location.longitude
                    )
                    WeatherNetworking.getUserCity(this, location.latitude,location.longitude) {
                        WeatherNetworking.getWeather(this) {
                            setUI()
                        }
                    }
                }
        } catch(e: SecurityException) {
            Log.v("Location permissions", "There was a problem with location services permissions")
        }

    }

    private fun setUI() {
        val date = DateUtils()
        binding.apply {
            currentDate.text = date.getDateFormatted()
            currentCity.text = WeatherRepository.getCity()
            currentTemp.text = "${WeatherRepository.getCurrentWeather().temp.toString()} \u2109"
            currentWeatherType.text = WeatherRepository.getCurrentWeather().weatherType.type
            currentWeatherIcon.setImageResource(
                WeatherRepository.getCurrentWeather().weatherType.drawable
            )
            today.text = "Today"
            weekly.text = "Weekly"
            weatherProgress.isInvisible = true
        }
        setHourlyWeather()

        today.setOnClickListener {
            setHourlyWeather()
        }

        weekly.setOnClickListener {
            setDailyWeather()
        }
    }

    private fun setDailyWeather() {
        binding.weatherItems.apply {
            adapter = dailyWeatherAdapter
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        }
        dailyWeatherAdapter.submitList(WeatherRepository.getDailyWeather())
    }

    private fun setHourlyWeather() {
        binding.weatherItems.apply {
            adapter = hourlyWeatherAdapter
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        }
        hourlyWeatherAdapter.submitList(WeatherRepository.getHourlyWeather())
    }
}