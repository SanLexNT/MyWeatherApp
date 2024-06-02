package com.example.myweatherapp.pojo.forecast

import com.example.myweatherapp.pojo.Main
import com.example.myweatherapp.pojo.Weather
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlin.collections.List

data class List(
    @SerializedName("dt")
    @Expose
    var dt: Int? = null,
    @SerializedName("main")
    @Expose
    var main: Main? = null,
    @SerializedName("weather")
    @Expose
    var weather: List<Weather>? = null
)
