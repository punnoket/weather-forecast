package com.pannawat.weatherforecast.network.model.forecast


import android.os.Parcelable
import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

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