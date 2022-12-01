package com.pannawat.weatherforecast.search.model

import android.os.Parcelable
import com.pannawat.weatherforecast.constant.UnitEnum
import com.pannawat.weatherforecast.model.weather.WeatherResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class CitySearchViewState(
    val weatherResponse: WeatherResponse? = null,
    val unit: UnitEnum = UnitEnum.CELSIUS
) : Parcelable
