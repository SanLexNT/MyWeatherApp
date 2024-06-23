package com.example.myweatherapp.presentation.currentWeather

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.myweatherapp.R
import com.example.myweatherapp.WeatherApplication
import com.example.myweatherapp.databinding.FragmentCurrentWeatherBinding
import com.example.myweatherapp.domain.model.CurrentWeather
import com.example.myweatherapp.presentation.ViewModelFactory
import com.example.myweatherapp.presentation.forecast.ForecastFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import javax.inject.Inject


class CurrentWeatherFragment : Fragment() {
    private lateinit var binding: FragmentCurrentWeatherBinding
    private lateinit var viewModel: CurrentWeatherViewModel
    private lateinit var city: String

    private val component by lazy {
        (requireActivity().application as WeatherApplication).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCurrentWeatherBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[CurrentWeatherViewModel::class.java]
        updateData()
    }


    private fun updateData() {
        city = requireActivity().getPreferences(
            MODE_PRIVATE
        ).getString(
            "cityName", null
        ) ?: resources.getString(R.string.default_city)

        viewModel.loadData(city)
        viewModel.currentWeather.observe(viewLifecycleOwner) {
            setupUI(it)
        }
    }


    @SuppressLint("SetTextI18n")
    private fun setupUI(currentWeather: CurrentWeather) {
        Log.d("MyTag", currentWeather.toString())
        binding.apply {
            textViewCity.text = city
            textViewData.text = currentWeather.time
            textViewDescription.text = currentWeather.condition
            textViewTemp.text = currentWeather.temp
            textViewWindSpeed.text = currentWeather.windSpeed
            textViewHumidity.text = currentWeather.humidity
            textViewClouds.text = currentWeather.clouds
            textViewRainfall.text = currentWeather.rainfall
            setWeatherIcon(currentWeather.endPointIcon)
            imageViewSearch.setOnClickListener {
                setupDialog()
            }

            imageViewIcon.setOnClickListener {
                if(resources.configuration.orientation != Configuration.ORIENTATION_LANDSCAPE){
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, ForecastFragment.newInstance())
                        .addToBackStack(null)
                        .commit()
                }
            }
        }
    }

    private fun setupDialog(){
        val view = LayoutInflater.from(requireContext())
            .inflate(R.layout.city_dialog_layout, null)
        val editTextCity = view.findViewById<TextInputEditText>(R.id.editTextCity)
        val dialog = MaterialAlertDialogBuilder(requireContext())
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


    private fun saveCity(city: String) {
        with(requireActivity().getPreferences(MODE_PRIVATE).edit()) {
            putString("cityName", city)
            apply()
        }
    }

    companion object{

        fun newInstance() : CurrentWeatherFragment {
            return CurrentWeatherFragment()
        }
    }
}


