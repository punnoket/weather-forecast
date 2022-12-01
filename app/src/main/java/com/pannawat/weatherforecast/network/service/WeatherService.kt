package com.pannawat.weatherforecast.network.service

import com.pannawat.weatherforecast.model.weather.WeatherResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface WeatherService {

    @GET("weather")
    fun getWeather(
        @QueryMap queryMap: Map<String, String>
    ): Single<WeatherResponse>
}
