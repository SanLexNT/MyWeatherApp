package com.example.myweatherapp.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
data class CurrentResponse(
    @SerializedName("coord")
    @Expose
    var coord: Coord? = null,
    @SerializedName("weather")
    @Expose
    var weather: java.util.List<Weather>? = null,
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