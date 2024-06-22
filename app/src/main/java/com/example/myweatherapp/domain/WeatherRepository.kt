package com.example.myweatherapp.domain

import com.example.myweatherapp.domain.model.CurrentWeather
import com.example.myweatherapp.domain.model.Forecast
import io.reactivex.rxjava3.core.Single

interface WeatherRepository {
    fun getCurrentWeather(city: String, apiKey: String): Single<CurrentWeather>
    fun getForecast(city: String, apiKey: String): Single<List<Forecast>>
}