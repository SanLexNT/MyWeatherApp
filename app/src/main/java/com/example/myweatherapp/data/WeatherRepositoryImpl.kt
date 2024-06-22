package com.example.myweatherapp.data

import com.example.myweatherapp.data.api.ApiFactory
import com.example.myweatherapp.data.mapper.CurrentWeatherMapper
import com.example.myweatherapp.data.mapper.ForecastMapper
import com.example.myweatherapp.data.model.CurrentResponse
import com.example.myweatherapp.data.model.ForecastResponse
import com.example.myweatherapp.domain.WeatherRepository
import com.example.myweatherapp.domain.model.CurrentWeather
import com.example.myweatherapp.domain.model.Forecast
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val currentWeatherMapper: CurrentWeatherMapper,
    private val forecastMapper: ForecastMapper
) : WeatherRepository {
    override fun getCurrentWeather(
        city: String,
        apiKey: String
    ): Single<CurrentWeather> {
        return currentWeatherMapper.currentResponseSingleToCurrentWeatherSingle(
            ApiFactory.getApiService().getCurrentWeather(city, apiKey)
        )
    }

    override fun getForecast(city: String, apiKey: String): Single<List<Forecast>> {
        return forecastMapper.forecastResponseSingleToForecastSingle(
            ApiFactory.getApiService().getForecast(city, apiKey)
        )
    }
}