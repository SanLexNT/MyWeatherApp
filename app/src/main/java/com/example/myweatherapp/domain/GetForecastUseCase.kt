package com.example.myweatherapp.domain

import com.example.myweatherapp.domain.model.Forecast
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetForecastUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    operator fun invoke(city: String, apiKey: String) : Single<List<Forecast>> {
        return repository.getForecast(city, apiKey)
    }
}