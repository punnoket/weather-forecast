package com.pannawat.weatherforecast.model.weather


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Keep
@JsonClass(generateAdapter = true)
@Parcelize
data class Wind(
    @Json(name = "deg")
    val deg: Int = 0,
    @Json(name = "gust")
    val gust: Double = 0.0,
    @Json(name = "speed")
    val speed: Double = 0.0
) : Parcelable