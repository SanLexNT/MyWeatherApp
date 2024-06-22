package com.example.myweatherapp.presentation

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myweatherapp.R
import com.example.myweatherapp.databinding.ActivityMainBinding
import com.example.myweatherapp.presentation.currentWeather.CurrentWeatherFragment
import com.example.myweatherapp.presentation.noInternet.NoInternetFragment
import com.example.myweatherapp.presentation.noInternet.NoInternetViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        val isConnected = capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true

        if(isConnected){
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, CurrentWeatherFragment.newInstance())
                .addToBackStack(null)
                .commit()
        } else{
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, NoInternetFragment.newInstance())
                .commit()
        }
    }
}