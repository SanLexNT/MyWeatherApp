package com.example.myweatherapp.screens.forecast

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myweatherapp.R
import com.example.myweatherapp.adapter.ForecastAdapter
import com.example.myweatherapp.databinding.ActivityForecastBinding
import com.example.myweatherapp.pojo.forecast.ForecastResponse

class ForecastActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForecastBinding
    private lateinit var viewModel: ForecastViewModel
    private lateinit var adapter: ForecastAdapter

    private lateinit var city: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForecastBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.let {
            it.title = getString(R.string.detail_forecast)
            it.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.main, null)))
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_back)
        }
        viewModel = ViewModelProvider(this)[ForecastViewModel::class.java]
        updateData()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            finish()
        }
        return true
    }

    private fun updateData() {
        city = getPreferences(
            MODE_PRIVATE
        ).getString(
            "cityName", null
        ) ?: resources.getString(R.string.default_city)

        viewModel.loadData(city)
        viewModel.response.observe(this) {
            Log.d("MyTag", it.toString())
            updateUI(it)
        }
    }

    private fun updateUI(forecastResponse: ForecastResponse) {
        adapter = ForecastAdapter()
        adapter.setList(forecastResponse.dailyForecast ?: emptyList())
        binding.apply {
            recyclerViewForecast.layoutManager = LinearLayoutManager(
                this@ForecastActivity, LinearLayoutManager.VERTICAL, false
            )
            recyclerViewForecast.adapter = adapter
        }
    }
}