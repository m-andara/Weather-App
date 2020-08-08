package com.example.weatherapp.Repository

object WeatherRepository {

    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private var city = ""

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