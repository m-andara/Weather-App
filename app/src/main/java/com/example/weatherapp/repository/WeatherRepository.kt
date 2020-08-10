package com.example.weatherapp.repository

import com.example.weatherapp.models.CurrentWeather
import com.example.weatherapp.models.DailyWeather

object WeatherRepository {

    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private var city = ""
    const val BASE_URL = "https://api.openweathermap.org/data/2.5"
    private val currentWeather = CurrentWeather()
    private val dailyWeather = listOf<DailyWeather>()

    fun setCoords(lat: Double, long: Double) {
        latitude = lat
        longitude = long
    }

    fun getCoords(): Pair<Double, Double> {
        return Pair(latitude, longitude)
    }

    fun setCity(currentCity: String) {
        city = currentCity
    }

    fun getCity() = city
}