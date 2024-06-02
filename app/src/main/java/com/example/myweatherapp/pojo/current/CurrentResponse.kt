package com.example.myweatherapp.pojo.current

import com.example.myweatherapp.pojo.Coord
import com.example.myweatherapp.pojo.Main
import com.example.myweatherapp.pojo.Weather
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
data class CurrentResponse(
    @SerializedName("coord")
    @Expose
    var coord: Coord? = null,
    @SerializedName("weather")
    @Expose
    var weather: List<Weather>? = null,
    @SerializedName("main")
    @Expose
    var main: Main? = null,
    @SerializedName("wind")
    @Expose
    var wind: Wind? = null,
    @SerializedName("rain")
    @Expose
    var rain: Rain? = null,
    @SerializedName("clouds")
    @Expose
    var clouds: Clouds? = null,
    @SerializedName("name")
    @Expose
    var name: String? = null

)