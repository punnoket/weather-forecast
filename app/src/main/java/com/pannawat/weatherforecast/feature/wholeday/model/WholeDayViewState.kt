package com.pannawat.weatherforecast.feature.wholeday.model

import android.os.Parcelable
import com.pannawat.weatherforecast.constant.UnitEnum
import com.pannawat.weatherforecast.network.model.forecast.ForecastResponse
import com.pannawat.weatherforecast.network.model.weather.WeatherResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class WholeDayViewState(
    val forecastResponse: ForecastResponse? = null,
    val unit: UnitEnum = UnitEnum.CELSIUS
) : Parcelable
