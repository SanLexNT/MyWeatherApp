package com.example.myweatherapp.presentation.forecast

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myweatherapp.domain.GetForecastUseCase
import com.example.myweatherapp.domain.model.Forecast
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class ForecastViewModel @Inject constructor(
    private val getForecastUseCase: GetForecastUseCase
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private var _response = MutableLiveData<List<Forecast>>()
    val response: LiveData<List<Forecast>>
        get() = _response


    fun loadData(city: String){
        val disposable = getForecastUseCase(city, API_KEY)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({forecasts ->
                _response.value = forecasts
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