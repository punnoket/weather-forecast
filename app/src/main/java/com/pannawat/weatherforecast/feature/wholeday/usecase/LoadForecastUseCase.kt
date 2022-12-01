package com.pannawat.weatherforecast.feature.wholeday.usecase

import com.pannawat.weatherforecast.network.model.forecast.ForecastResponse
import com.pannawat.weatherforecast.network.repository.WeatherRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class LoadForecastUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {

    fun execute(
        cityName: String, units: String
    ): Single<ForecastResponse> {
        return weatherRepository.getForecast(
            mapOf("q" to cityName, "units" to units, "cnt" to "3")
        )
    }
}
