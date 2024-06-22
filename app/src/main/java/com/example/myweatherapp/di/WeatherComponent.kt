package com.example.myweatherapp.di

import android.app.Application
import com.example.myweatherapp.presentation.currentWeather.CurrentWeatherFragment
import com.example.myweatherapp.presentation.forecast.ForecastFragment
import com.example.myweatherapp.presentation.noInternet.NoInternetFragment
import dagger.BindsInstance
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
    fun inject(noInternetFragment: NoInternetFragment)

    @Component.Factory
    interface WeatherComponentFactory{
        fun create(
            @BindsInstance application: Application
        ) : WeatherComponent
    }
}