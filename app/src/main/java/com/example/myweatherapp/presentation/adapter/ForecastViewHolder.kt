package com.example.myweatherapp.presentation.adapter

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.myweatherapp.R
import com.example.myweatherapp.databinding.DailyForecastLayoutBinding
import com.example.myweatherapp.domain.model.Forecast
import com.squareup.picasso.Picasso

class ForecastViewHolder(private val binding: DailyForecastLayoutBinding) :
    ViewHolder(binding.root) {
    fun bind(forecast: Forecast) {
        with(binding) {
            textViewData.text = forecast.day
            textViewTemp.text = forecast.temp
            setWeatherIcon(forecast.endPointIcon, imageViewIcon)
        }
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