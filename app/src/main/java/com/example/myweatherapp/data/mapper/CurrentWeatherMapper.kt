package com.example.myweatherapp.data.mapper

import android.annotation.SuppressLint
import com.example.myweatherapp.data.model.CurrentResponse
import com.example.myweatherapp.domain.model.CurrentWeather
import io.reactivex.rxjava3.core.Single
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class CurrentWeatherMapper @Inject constructor() {
    private fun currentResponseToCurrentWeather(currentResponse: CurrentResponse): CurrentWeather {
        val condition = currentResponse.weather?.get(0)?.description?.replaceFirstChar {
            it.titlecase(Locale.getDefault())
        }
        val rainfall = if(currentResponse.rain?.sizeRain == null){
            "Нет"
        } else{
            currentResponse.rain?.sizeRain.toString() + " мм"
        }
        return CurrentWeather(
            cityName = currentResponse.name ?: EMPTY_CHAR,
            time = convertCurrentTimeToDate(),
            condition = condition ?: EMPTY_CHAR,
            temp = "${currentResponse.main?.temp?.toInt().toString()} C",
            windSpeed = "${currentResponse.wind?.speed?.toString()} м/с",
            humidity = "${currentResponse.main?.humidity.toString()} %",
            clouds = "${currentResponse.clouds?.all.toString()} %",
            rainfall = rainfall,
            endPointIcon = currentResponse.weather?.get(0)?.icon.toString()
        )
    }
    fun currentResponseSingleToCurrentWeatherSingle(currentResponse: Single<CurrentResponse>)
            : Single<CurrentWeather> {
        return currentResponse.map{
            currentResponseToCurrentWeather(it)
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun convertCurrentTimeToDate(): String {
        val currentMillis = System.currentTimeMillis()
        val date = Date(currentMillis)
        val format = SimpleDateFormat("dd.MM.yyyy")
        return format.format(date)
    }

    companion object {
        private const val EMPTY_CHAR = ""
    }
}