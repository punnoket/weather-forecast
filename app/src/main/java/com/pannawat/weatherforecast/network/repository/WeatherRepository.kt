package com.pannawat.weatherforecast.network.repository

import com.pannawat.weatherforecast.network.model.forecast.ForecastResponse
import com.pannawat.weatherforecast.network.model.weather.WeatherResponse
import com.pannawat.weatherforecast.provider.AppNetworkingProvider
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val appNetworkingProvider: AppNetworkingProvider
) {
    companion object {
        @JvmStatic
        private val TAG = WeatherRepository::class.java.simpleName
    }

    fun getWeather(queryMap: Map<String, String>): Single<WeatherResponse> {
        return appNetworkingProvider.weatherService.getWeather(queryMap)
    }

    fun getForecast(queryMap: Map<String, String>): Single<ForecastResponse> {
        return appNetworkingProvider.weatherService.getForecast(queryMap)
    }
}
