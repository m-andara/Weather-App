package com.example.weatherapp.networking

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("/onecall?exclude=minutely,hourly&units=imperial&{lat}&{lon}&{id}")
    fun getWeather(@Query("lat") lat: Double, @Query("lon") lon: Double, @Query("appid") id: String): Call<CurrentWeather>
}