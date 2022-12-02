package com.pannawat.weatherforecast.feature.wholeday.usecase

import com.pannawat.weatherforecast.network.model.forecast.ForecastResponse
import com.pannawat.weatherforecast.network.repository.ForecastRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class LoadForecastUseCase @Inject constructor(
    private val forecastRepository: ForecastRepository
) {

    fun execute(
        cityName: String, units: String
    ): Single<ForecastResponse> {
        return forecastRepository.getForecast(
            mapOf("q" to cityName, "units" to units, "cnt" to "3")
        )
    }
}
