package com.example.myweatherapp.di

import com.example.myweatherapp.data.WeatherRepositoryImpl
import com.example.myweatherapp.domain.WeatherRepository
import dagger.Binds
import dagger.Module

@Module
interface DataModule {
    @Binds
    fun bindWeatherRepository(impl: WeatherRepositoryImpl) : WeatherRepository

}