package com.pannawat.weatherforecast.network.model.forecast


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Keep
@JsonClass(generateAdapter = true)
@Parcelize
data class Coord(
    @Json(name = "lat")
    val lat: Double = 0.0,
    @Json(name = "lon")
    val lon: Double = 0.0
) : Parcelable