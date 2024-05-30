package com.example.myweatherapp.pojo.forecast

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlin.collections.List

data class ForecastResponse(
    @SerializedName("cnt")
    @Expose
    var days: Int? = null,

    @SerializedName("list")
    @Expose
    var dailyForecast: List<com.example.myweatherapp.pojo.forecast.List>? = null
)