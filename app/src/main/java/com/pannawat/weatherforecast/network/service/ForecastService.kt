package com.pannawat.weatherforecast.network.service

import com.pannawat.weatherforecast.network.model.forecast.ForecastResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ForecastService {

    @GET("forecast")
    fun getForecast(
        @QueryMap queryMap: Map<String, String>
    ): Single<ForecastResponse>
}
