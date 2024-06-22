package com.example.myweatherapp.domain

import com.example.myweatherapp.domain.model.CurrentWeather
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetCurrentWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    operator fun invoke(city: String, apiKey: String): Single<CurrentWeather> {
        return repository.getCurrentWeather(city, apiKey)
    }
}