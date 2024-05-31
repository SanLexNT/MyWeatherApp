package com.example.myweatherapp.screens.weather

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.myweatherapp.R
import com.example.myweatherapp.databinding.ActivityMainBinding
import com.example.myweatherapp.databinding.ActivityWeatherBinding
import com.example.myweatherapp.pojo.current.CurrentResponse
import com.example.myweatherapp.screens.city.MainActivity
import com.example.myweatherapp.screens.city.MainViewModel
import com.example.myweatherapp.screens.forecast.ForecastActivity
import java.sql.Date
import java.text.SimpleDateFormat

class WeatherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWeatherBinding
    private lateinit var viewModel: WeatherViewModel
    private lateinit var city: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
        city = intent.getStringExtra("city") ?: getSharedPreferences(
            "prefCity",
            MODE_PRIVATE
        ).getString(
            "cityName", null
        ) ?: resources.getString(R.string.default_city)

        viewModel.loadData(city)

        viewModel.response.observe(this) {
            setupUI(it)
        }
    }

    private fun setupUI(currentResponse: CurrentResponse) {
        binding.apply {
            textViewCity.text = city
            textViewData.text = convertCurrentTimeToDate()
            textViewTemp.text = currentResponse.main?.temp.toString() + " C"
            textViewWindSpeed.text = currentResponse.wind?.speed.toString() + " м/с"
            textViewHumidity.text = currentResponse.main?.humidity.toString() + " %"
            textViewRainfall.text = currentResponse.rain?.sizeRain.toString() + "мм"
            textViewDescription.text = convertWeatherCondition(currentResponse.weather?.get(0)?.main)

            imageViewSearch.setOnClickListener {
                startActivity(Intent(this@WeatherActivity, MainActivity::class.java))
            }

            buttonForecast.setOnClickListener {
                startActivity(Intent(this@WeatherActivity, ForecastActivity::class.java))
            }
        }
    }

    private fun convertWeatherCondition(condition: String?): String {
        val arrayConditions = resources.getStringArray(R.array.conditions)
        return when (condition) {
            "clear sky" -> arrayConditions[0]
            "few clouds" -> arrayConditions[1]
            "scattered clouds" -> arrayConditions[2]
            "broken clouds" -> arrayConditions[3]
            "shower rain" -> arrayConditions[4]
            "rain" -> arrayConditions[5]
            "thunderstorm" -> arrayConditions[6]
            "snow" -> arrayConditions[7]
            "mist" -> arrayConditions[8]
            else -> arrayConditions[0]
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun convertCurrentTimeToDate(): String {
        val currentMillis = System.currentTimeMillis()
        val date = Date(currentMillis)
        val format = SimpleDateFormat("dd.MM.yyyy")
        return format.format(date)
    }
}