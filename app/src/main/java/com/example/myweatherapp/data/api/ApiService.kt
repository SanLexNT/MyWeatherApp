package com.example.myweatherapp.data.api

import com.example.myweatherapp.data.model.CurrentResponse
import com.example.myweatherapp.data.model.ForecastResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("weather?units=metric&lang=ru")
    fun getCurrentWeather(@Query("q") city: String, @Query("appid") apiKey: String)
        : Single<CurrentResponse>

    @GET("forecast?units=metric&lang=ru")
    fun getForecast(@Query("q") city: String,
                    @Query("appid") apiKey: String
    ) : Single<ForecastResponse>


}