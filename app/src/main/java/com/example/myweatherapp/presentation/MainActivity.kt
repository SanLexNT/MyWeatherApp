package com.example.myweatherapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myweatherapp.R
import com.example.myweatherapp.databinding.ActivityMainBinding
import com.example.myweatherapp.presentation.currentWeather.CurrentWeatherFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, CurrentWeatherFragment.newInstance())
            .addToBackStack(null)
            .commit()

    }
}