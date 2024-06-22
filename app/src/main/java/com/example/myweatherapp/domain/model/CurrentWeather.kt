package com.example.myweatherapp.domain.model

data class CurrentWeather(
    val cityName: String,
    val condition: String,
    val time: String,
    val temp: String,
    val windSpeed: String,
    val humidity: String,
    val clouds: String,
    val rainfall: String,
    val endPointIcon: String
)
