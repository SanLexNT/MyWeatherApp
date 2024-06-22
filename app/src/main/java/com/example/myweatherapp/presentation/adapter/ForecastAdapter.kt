package com.example.myweatherapp.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.myweatherapp.R
import com.example.myweatherapp.domain.model.Forecast
import com.squareup.picasso.Picasso

class ForecastAdapter : ListAdapter<Forecast, ForecastAdapter.ForecastViewHolder>(ForecastCallBack()) {
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

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val forecastInDay = currentList[position]
        holder.textViewData.text = forecastInDay.day
        holder.textViewTemp.text = forecastInDay.temp
        setWeatherIcon(forecastInDay.endPointIcon, holder.imageViewIcon)
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