package com.example.myweatherapp.data.model

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
