package com.example.myweatherapp.api

import com.example.myweatherapp.pojo.current.CurrentResponse
import com.example.myweatherapp.pojo.forecast.ForecastResponse
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("weather?units=metric&lang=ru")
    fun getCurrentWeather(@Query("q") city: String, @Query("appid") apiKey: String = API_KEY) : Single<CurrentResponse>

    @GET("forecast/daily?")
    fun getForecast(@Query("q") city: String, @Query("cnt") days: Int = 7,
                    @Query("appid") apiKey: String = API_KEY) : Single<ForecastResponse>

    companion object{
        private const val API_KEY = "953a51f05607ba50bb010849c464cef2"
    }
}