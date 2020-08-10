package com.example.weatherapp.networking

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CurrentWeather (
    @Json(name = "current") val currentWeather: CurrentWeatherItems,
    @Json(name = "daily") val daily: List<DailyWeather>
)

@JsonClass(generateAdapter = true)
data class CurrentWeatherItems (
    @Json(name = "temp") val temp: Double,
    @Json(name = "humidity") val humidity: Int,
    @Json(name = "feels_like") val feelsLikeTemp: Double,
    @Json(name = "weather") val weather: Weather
)

@JsonClass(generateAdapter = true)
data class Weather(
    @Json(name = "main") val weatherType: String,
    @Json(name = "description") val description: String
)

@JsonClass(generateAdapter = true)
data class DailyWeather(
    @Json(name = "temp") val temp: Temp,
    @Json(name = "feels_like") val feelsLike: DailyFeelsLike,
    @Json(name = "humidity") val humidity: Int,
    @Json(name = "weather") val weather: Weather
)

@JsonClass(generateAdapter = true)
data class Temp(
    @Json(name = "day") val dayTemp: Double,
    @Json(name = "night") val nightTemp: Double
)

@JsonClass(generateAdapter = true)
data class DailyFeelsLike(
    @Json(name = "day") val dayLike: Double,
    @Json(name = "night") val nightLike: Double
)