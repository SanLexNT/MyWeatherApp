package com.example.myweatherapp.presentation.noInternet

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.myweatherapp.R
import com.example.myweatherapp.WeatherApplication
import com.example.myweatherapp.databinding.FragmentNoInternetBinding
import com.example.myweatherapp.di.DaggerWeatherComponent
import com.example.myweatherapp.domain.model.CurrentWeather
import com.example.myweatherapp.presentation.ViewModelFactory
import com.example.myweatherapp.presentation.currentWeather.CurrentWeatherFragment
import javax.inject.Inject

class NoInternetFragment : Fragment() {
    private lateinit var binding: FragmentNoInternetBinding
    private lateinit var viewModel: NoInternetViewModel

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
        binding = FragmentNoInternetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[NoInternetViewModel::class.java]

        viewModel.hasInternetConnection.observe(viewLifecycleOwner) { value ->
            if (value) {
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, CurrentWeatherFragment.newInstance())
                    .commit()
            }
        }

        binding.imageViewInternet.setOnClickListener {
            viewModel.checkConnection()
        }
    }

    companion object {
        fun newInstance(): NoInternetFragment {
            return NoInternetFragment()
        }
    }
}