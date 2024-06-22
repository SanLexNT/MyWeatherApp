package com.example.myweatherapp.presentation.forecast

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myweatherapp.R
import com.example.myweatherapp.WeatherApplication
import com.example.myweatherapp.databinding.FragmentForecastBinding
import com.example.myweatherapp.domain.model.Forecast
import com.example.myweatherapp.presentation.ViewModelFactory
import com.example.myweatherapp.presentation.adapter.ForecastAdapter
import javax.inject.Inject

class ForecastFragment : Fragment() {
    private lateinit var binding: FragmentForecastBinding
    private lateinit var viewModel: ForecastViewModel
    private lateinit var adapter: ForecastAdapter

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
        binding = FragmentForecastBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[ForecastViewModel::class.java]
        updateData()

        requireActivity().apply {
            onBackPressedDispatcher.addCallback(viewLifecycleOwner){
                supportFragmentManager.popBackStack()
            }
        }
    }


    private fun updateData() {
        city = requireActivity().getPreferences(
            MODE_PRIVATE
        ).getString(
            "cityName", null
        ) ?: resources.getString(R.string.default_city)

        viewModel.loadData(city)
        viewModel.response.observe(viewLifecycleOwner) {
            Log.d("MyTag", it.toString())
            updateUI(it)
        }
    }

    private fun updateUI(forecastList: List<Forecast>) {
        adapter = ForecastAdapter()
        adapter.submitList(forecastList)
        binding.apply {
            recyclerViewForecast.layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.VERTICAL, false
            )
            recyclerViewForecast.adapter = adapter
        }
    }

    companion object{
        fun newInstance() : ForecastFragment{
            return ForecastFragment()
        }
    }

}