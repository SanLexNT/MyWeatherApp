package com.example.myweatherapp.di

import com.example.myweatherapp.presentation.currentWeather.CurrentWeatherFragment
import com.example.myweatherapp.presentation.forecast.ForecastFragment
import dagger.Component

@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)
interface WeatherComponent {
    fun inject(currentWeatherFragment: CurrentWeatherFragment)
    fun inject(forecastFragment: ForecastFragment)
}