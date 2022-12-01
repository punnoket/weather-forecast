package com.pannawat.weatherforecast.network.model.forecast


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Keep
@JsonClass(generateAdapter = true)
@Parcelize
data class ForecastResponse(
    @Json(name = "city")
    val city: City = City(),
    @Json(name = "cnt")
    val cnt: Int = 0,
    @Json(name = "cod")
    val cod: String = "",
    @Json(name = "list")
    val list: List<Forecast> = listOf(),
    @Json(name = "message")
    val message: Int = 0
) : Parcelable