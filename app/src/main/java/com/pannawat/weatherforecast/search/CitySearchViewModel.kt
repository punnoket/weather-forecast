package com.pannawat.weatherforecast.search

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.pannawat.weatherforecast.SchedulersProvider
import com.pannawat.weatherforecast.base.BaseViewModel
import com.pannawat.weatherforecast.constant.UnitEnum
import com.pannawat.weatherforecast.model.weather.WeatherResponse
import com.pannawat.weatherforecast.search.model.CitySearchViewState
import com.pannawat.weatherforecast.search.usecase.SearchWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

@HiltViewModel
class CitySearchViewModel @Inject constructor(
    private val searchWeatherUseCase: SearchWeatherUseCase,
    private val schedulersProvider: SchedulersProvider,
) : BaseViewModel() {
    companion object {
        @JvmStatic
        private val TAG = CitySearchViewModel::class.java.simpleName
    }

    // View State
    val citySearchViewState by lazy {
        MediatorLiveData<CitySearchViewState>().apply {
            addSource(weatherResponseLiveData) { source ->
                value = value?.copy(
                    weatherResponse = source
                ) ?: CitySearchViewState(weatherResponse = source)
            }
            addSource(unitLiveData) { source ->
                value = value?.copy(
                    unit = source
                ) ?: CitySearchViewState(unit = source)
            }
        }
    }

    // Live Data
    private val weatherResponseLiveData by lazy { MutableLiveData<WeatherResponse>() }
    private val unitLiveData by lazy { MutableLiveData<UnitEnum>() }
    private val cityNameLiveData by lazy { MutableLiveData<String>() }

    fun searchWeatherByName(cityName: String) {
        cityNameLiveData.value = cityName
        searchWeather()
    }

    fun searchWeatherByUnit(unitEnum: UnitEnum) {
        unitLiveData.value = unitEnum
        searchWeather()
    }

    private fun searchWeather() {
        disposables += searchWeatherUseCase.execute(
            cityNameLiveData.value ?: "",
            unitLiveData.value?.value ?: UnitEnum.CELSIUS.value
        )
            .subscribeOn(schedulersProvider.io)
            .observeOn(schedulersProvider.ui)
            .doOnSubscribe {
                showLoading()
            }
            .doOnEvent { _, _ ->
                hideLoading()
            }
            .doOnError {
                showError(it)
            }
            .subscribeBy(
                onError = {},
                onSuccess = {
                    weatherResponseLiveData.value = it
                }
            )
    }
}