package com.example.myweatherapp.data.mapper

import com.example.myweatherapp.data.model.ForecastResponse
import com.example.myweatherapp.domain.model.Forecast
import io.reactivex.rxjava3.core.Single
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class ForecastMapper @Inject constructor(){
    private fun forecastResponseToForecast(forecastResponse: ForecastResponse): List<Forecast> {

        val dailyForecastDto = forecastResponse.dailyForecast ?: emptyList()
        val dailyForecast = mutableListOf<Forecast>()

        for (day in dailyForecastDto){
            dailyForecast.add(Forecast(
                day = convertLongToDate(day.dt?.toLong() ?: 0),
                temp = "${day.main?.temp?.toInt().toString()} C",
                endPointIcon = day.weather?.get(0)?.icon ?: EMPTY_CHAR
            ))
        }

        return dailyForecast
    }

    fun forecastResponseSingleToForecastSingle(forecastResponse: Single<ForecastResponse>)
            : Single<List<Forecast>> {
        return forecastResponse.map{
            forecastResponseToForecast(it)
        }
    }

    private fun convertLongToDate(dateLong: Long): String {
        // Преобразуем Unix-время в LocalDateTime
        val utcTime = LocalDateTime.ofEpochSecond(dateLong, 0, ZoneOffset.UTC)

        // Преобразуем время из UTC в Московскую временную зону
        val moscowZoneId = ZoneId.of("Europe/Moscow")
        val moscowTime = utcTime.atZone(ZoneOffset.UTC).withZoneSameInstant(moscowZoneId)

        // Форматируем дату и время в Московской временной зоне
        val formatter = DateTimeFormatter.ofPattern("dd.MM / HH:mm")
        return moscowTime.format(formatter)
    }

    companion object {
        private const val EMPTY_CHAR = ""
    }
}