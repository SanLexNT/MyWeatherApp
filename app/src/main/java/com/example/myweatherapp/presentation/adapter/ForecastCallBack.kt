package com.example.myweatherapp.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.myweatherapp.domain.model.Forecast

class ForecastCallBack : DiffUtil.ItemCallback<Forecast>() {
    override fun areItemsTheSame(oldItem: Forecast, newItem: Forecast): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Forecast, newItem: Forecast): Boolean {
        return areItemsTheSame(oldItem, newItem)
    }
}