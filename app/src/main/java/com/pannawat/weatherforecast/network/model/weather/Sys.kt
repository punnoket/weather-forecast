package com.pannawat.weatherforecast.network.model.weather


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Keep
@JsonClass(generateAdapter = true)
@Parcelize
data class Sys(
    @Json(name = "country")
    val country: String = "",
    @Json(name = "sunrise")
    val sunrise: Int = 0,
    @Json(name = "sunset")
    val sunset: Int = 0
) : Parcelable