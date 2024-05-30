package com.example.myweatherapp.pojo.forecast

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Temp(
    @SerializedName("day")
    @Expose
    var day: Double? = null,
    @SerializedName("min")
    @Expose
    var min: Double? = null,
    @SerializedName("max")
    @Expose
    var max: Double? = null
)
