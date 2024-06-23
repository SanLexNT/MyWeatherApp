package com.example.myweatherapp.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.myweatherapp.databinding.DailyForecastLayoutBinding
import com.example.myweatherapp.domain.model.Forecast

class ForecastAdapter : ListAdapter<Forecast, ForecastViewHolder>(ForecastCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val binding = DailyForecastLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ForecastViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val forecastInDay = currentList[position]
        holder.bind(forecastInDay)
    }
}