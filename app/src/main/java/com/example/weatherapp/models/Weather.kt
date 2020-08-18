package com.example.weatherapp.models

import androidx.annotation.DrawableRes
import com.example.weatherapp.R
import com.example.weatherapp.networking.Weather
import com.squareup.moshi.Json


enum class WeatherTypeImage(
    val type: String,
    @DrawableRes val drawable: Int
) {
    THUNDERSTORM("thunderstorm", R.drawable.rain_lightning_cloud),
    DRIZZLE("drizzle", R.drawable.drizzle_cloud),
    RAIN("rain", R.drawable.heavy_rain_cloud),
    SNOW("snow", R.drawable.snow_cloud),
    CLEAR("clear", R.drawable.sun),
    CLOUDS("clouds", R.drawable.clouds),
    OTHER("other", R.drawable.fog);

    companion object {
        fun of(type: String) = when(type.toLowerCase()) {
                "thunderstorm" -> THUNDERSTORM
                "drizzle" -> DRIZZLE
                "rain" -> RAIN
                "snow" -> SNOW
                "clear" -> CLEAR
                "clouds" -> CLOUDS
                else -> OTHER
            }
    }
}

data class Weather (
    var temp: Double,
    var humidity: Int,
    var feelsLikeTemp: Double,
    var weatherType: WeatherTypeImage,
    var weatherDesc: String
) {
    constructor(): this(0.0, 0, 0.0, WeatherTypeImage.OTHER, "")
}

data class DailyWeather (
    val dayTemp: Double,
    val nightTemp: Double,
    val dayLike: Double,
    val nightLike: Double,
    val humidity: Int,
    val weatherType: WeatherTypeImage,
    val weatherDesc: String
)