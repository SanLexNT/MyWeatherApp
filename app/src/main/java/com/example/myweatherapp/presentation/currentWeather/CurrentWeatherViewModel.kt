package com.example.myweatherapp.presentation.currentWeather

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myweatherapp.domain.GetCurrentWeatherUseCase
import com.example.myweatherapp.domain.WeatherRepository
import com.example.myweatherapp.domain.model.CurrentWeather
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class CurrentWeatherViewModel @Inject constructor(
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private var _currentWeather = MutableLiveData<CurrentWeather>()
    val currentWeather: LiveData<CurrentWeather>
        get() = _currentWeather


    fun loadData(city: String){
        val disposable = getCurrentWeatherUseCase(city, API_KEY)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({weather ->
                _currentWeather.value = weather
            }, {error ->
                run {
                    Log.e("App Errors", error.message.toString())
                }
            })

        compositeDisposable.add(disposable)
    }



    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    companion object{
        private const val API_KEY = "953a51f05607ba50bb010849c464cef2"
    }
}
