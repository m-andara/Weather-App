package com.example.weatherapp.repository

import com.example.weatherapp.models.Weather
import com.example.weatherapp.models.DailyWeather
import com.example.weatherapp.models.WeatherTypeImage

object WeatherRepository {

    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private var city = ""
    const val BASE_URL = "https://api.openweathermap.org"
    private var currentWeather = Weather(
        0.0, 0, 0.0, WeatherTypeImage.OTHER, ""
    )
    private val dailyWeather = mutableListOf<DailyWeather>()
    private val hourlyWeather = mutableListOf<Weather>()

    fun setCurrentWeather(weather: Weather) {
        currentWeather.temp = weather.temp
        currentWeather.humidity = weather.humidity
        currentWeather.feelsLikeTemp = weather.feelsLikeTemp
        currentWeather.weatherType = weather.weatherType
        currentWeather.weatherDesc = weather.weatherDesc
    }

    fun getCurrentWeather(): Weather {
        return currentWeather
    }

    fun setHourlyWeather(weather: List<Weather>) {
        hourlyWeather.clear()
        hourlyWeather.addAll(weather)
    }

    fun getHourlyWeather(): List<Weather> {
        return hourlyWeather
    }

    fun getDailyWeather(): List<DailyWeather> {
        return dailyWeather
    }

    fun setDailyWeather(weather: List<DailyWeather>) {
        dailyWeather.clear()
        dailyWeather.addAll(weather)
    }

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