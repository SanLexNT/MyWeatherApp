package com.example.myweatherapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.myweatherapp.R
import com.squareup.picasso.Picasso
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class ForecastAdapter : Adapter<ForecastAdapter.ForecastViewHolder>() {

    private var forecastList: List<com.example.myweatherapp.pojo.forecast.List> = emptyList()

    class ForecastViewHolder(v: View) : ViewHolder(v) {
        val textViewData: TextView = v.findViewById(R.id.textViewData)
        val textViewTemp: TextView = v.findViewById(R.id.textViewTemp)
        val imageViewIcon: ImageView = v.findViewById(R.id.imageViewIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.daily_forecast_layout, parent, false)
        return ForecastViewHolder(view)
    }

    override fun getItemCount(): Int = forecastList.size

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val day = forecastList[position]
        val tempString = day.main?.temp?.toInt().toString()
        holder.textViewData.text = convertLongToDate(day.dt?.toLong() ?:
                    System.currentTimeMillis())
        holder.textViewTemp.text = "$tempString C"
        setWeatherIcon(day.weather?.get(0)?.icon, holder.imageViewIcon)
    }

    fun setList(dailyForecastList: List<com.example.myweatherapp.pojo.forecast.List>) {
        forecastList = dailyForecastList
        notifyDataSetChanged()
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

    private fun setWeatherIcon(iconEndPoint: String?, imageView: ImageView) {
        if (iconEndPoint == null) {
            imageView.setImageResource(R.drawable.default_weather)
        } else {
            val url = "https://openweathermap.org/img/wn/$iconEndPoint.png"
            Picasso.get()
                .load(url)
                .fit()
                .into(imageView)
        }
    }
}