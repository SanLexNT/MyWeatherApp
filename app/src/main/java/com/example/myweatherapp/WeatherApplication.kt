package com.example.myweatherapp

import android.app.Application
import com.example.myweatherapp.di.DaggerWeatherComponent

class WeatherApplication : Application() {
    val component by lazy {
        DaggerWeatherComponent.factory().create(this)
    }
}