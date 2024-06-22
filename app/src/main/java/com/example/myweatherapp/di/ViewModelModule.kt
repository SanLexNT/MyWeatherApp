package com.example.myweatherapp.di

import androidx.lifecycle.ViewModel
import com.example.myweatherapp.presentation.currentWeather.CurrentWeatherViewModel
import com.example.myweatherapp.presentation.forecast.ForecastViewModel
import com.example.myweatherapp.presentation.noInternet.NoInternetViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @Binds
    @ViewModelKey(CurrentWeatherViewModel::class)
    @IntoMap
    fun bindCurrentWeatherViewModel(currentWeatherViewModel : CurrentWeatherViewModel):ViewModel

    @Binds
    @ViewModelKey(ForecastViewModel::class)
    @IntoMap
    fun bindForecastViewModel(forecastViewModel : ForecastViewModel):ViewModel

    @Binds
    @ViewModelKey(NoInternetViewModel::class)
    @IntoMap
    fun bindNoInternetViewModelViewModel(noInternetViewModel: NoInternetViewModel):ViewModel
}