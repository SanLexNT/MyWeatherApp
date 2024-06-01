package com.example.myweatherapp.pojo.current

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Rain(
    @SerializedName("1h")
    @Expose
    var sizeRain: Double? = null
)
