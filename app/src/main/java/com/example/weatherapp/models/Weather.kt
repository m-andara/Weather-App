package com.example.weatherapp.models

import com.example.weatherapp.networking.Weather
import com.squareup.moshi.Json

data class CurrentWeather (
    val temp: Double,
    val humidity: Int,
    val feelsLikeTemp: Double,
    val weatherType: String,
    val weatherDesc: String
) {
    constructor(): this(0.0, 0, 0.0, "", "")
}

data class DailyWeather (
    val dayTemp: Double,
    val nightTemp: Double,
    val dayLike: Double,
    val nightLike: Double,
    val humidity: Int,
    val weatherType: String,
    val weatherDesc: String
)