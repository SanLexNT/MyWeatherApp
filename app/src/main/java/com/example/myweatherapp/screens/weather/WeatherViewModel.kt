package com.example.myweatherapp.screens.weather

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.myweatherapp.api.ApiFactory
import com.example.myweatherapp.pojo.current.CurrentResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class WeatherViewModel(application: Application) : AndroidViewModel(application) {
    private val compositeDisposable = CompositeDisposable()
    private val apiService = ApiFactory.getApiService()
    val response = MutableLiveData<CurrentResponse>()

    fun loadData(city: String){
        val disposable = apiService.getCurrentWeather(city)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({weather ->
                response.value = weather
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
}