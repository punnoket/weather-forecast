package com.pannawat.weatherforecast.network.repository

import com.pannawat.weatherforecast.network.model.forecast.ForecastResponse
import com.pannawat.weatherforecast.network.model.weather.WeatherResponse
import com.pannawat.weatherforecast.provider.AppNetworkingProvider
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ForecastRepository @Inject constructor(
    private val appNetworkingProvider: AppNetworkingProvider
) {
    companion object {
        @JvmStatic
        private val TAG = ForecastRepository::class.java.simpleName
    }

    fun getForecast(queryMap: Map<String, String>): Single<ForecastResponse> {
        return appNetworkingProvider.forecastService.getForecast(queryMap)
    }
}
