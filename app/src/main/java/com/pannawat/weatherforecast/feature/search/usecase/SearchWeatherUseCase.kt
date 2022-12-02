package com.pannawat.weatherforecast.feature.search.usecase

import com.pannawat.weatherforecast.network.model.weather.WeatherResponse
import com.pannawat.weatherforecast.network.repository.WeatherRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class SearchWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {

    fun execute(
        cityName: String, units: String
    ): Single<WeatherResponse> {
        return weatherRepository.getWeather(
            mapOf(
                "q" to cityName,
                "units" to units
            )
        )
    }
}
