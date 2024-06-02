package com.example.myweatherapp.screens.main

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.myweatherapp.R
import com.example.myweatherapp.databinding.ActivityMainBinding
import com.example.myweatherapp.pojo.current.CurrentResponse
import com.example.myweatherapp.screens.forecast.ForecastActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var city: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        updateData()
    }


    private fun updateData(){
        city = getPreferences(
            MODE_PRIVATE
        ).getString(
            "cityName", null
        ) ?: resources.getString(R.string.default_city)

        viewModel.loadData(city)
        viewModel.response.observe(this) {
            setupUI(it)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupUI(currentResponse: CurrentResponse) {
        Log.d("MyTag", currentResponse.toString())
        binding.apply {
            val rainfall = currentResponse.rain?.sizeRain ?: 0
            val condition = currentResponse.weather?.get(0)?.description?.replaceFirstChar {
                it.titlecase(Locale.getDefault())
            }

            textViewCity.text = city
            textViewData.text = convertCurrentTimeToDate()
            textViewDescription.text = condition
            textViewTemp.text = currentResponse.main?.temp?.toInt().toString() + " C"
            textViewWindSpeed.text = currentResponse.wind?.speed.toString() + " м/с"
            textViewHumidity.text = currentResponse.main?.humidity.toString() + " %"
            textViewClouds.text = currentResponse.clouds?.all.toString() + " %"
            textViewRainfall.text = "$rainfall мм"
            setWeatherIcon(currentResponse.weather?.get(0)?.icon)
            imageViewSearch.setOnClickListener {
                val view = LayoutInflater.from(this@MainActivity)
                    .inflate(R.layout.city_dialog_layout, null)
                val editTextCity = view.findViewById<TextInputEditText>(R.id.editTextCity)
                val dialog = MaterialAlertDialogBuilder(this@MainActivity)
                    .setTitle(getString(R.string.enter_city_title))
                    .setView(view)
                    .setPositiveButton("OK") { dialogInterface: DialogInterface, _: Int ->
                        val city = editTextCity.text?.trim().toString()
                        saveCity(city)
                        dialogInterface.dismiss()
                        updateData()
                    }
                    .setNegativeButton(getString(R.string.cancel_button)) { dialogInterface: DialogInterface, _: Int ->
                        dialogInterface.dismiss()
                    }.create()

                dialog.show()
            }

            imageViewIcon.setOnClickListener {
                startActivity(Intent(this@MainActivity, ForecastActivity::class.java))
            }
        }
    }

    private fun setWeatherIcon(iconEndPoint: String?) {
        if (iconEndPoint == null) {
            binding.imageViewIcon.setImageResource(R.drawable.default_weather)
        } else {
            lifecycleScope.launch {
                val url = "https://openweathermap.org/img/wn/$iconEndPoint@2x.png"
                Picasso.get()
                    .load(url)
                    .fit()
                    .into(binding.imageViewIcon)
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun convertCurrentTimeToDate(): String {
        val currentMillis = System.currentTimeMillis()
        val date = Date(currentMillis)
        val format = SimpleDateFormat("dd.MM.yyyy")
        return format.format(date)
    }

    private fun saveCity(city: String) {
        with(getPreferences(MODE_PRIVATE).edit()) {
            putString("cityName", city)
            apply()
        }
    }
}