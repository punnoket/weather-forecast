package com.pannawat.weatherforecast.network.model.forecast


import android.os.Parcelable
import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Keep
@JsonClass(generateAdapter = true)
@Parcelize
data class City(
    @Json(name = "coord")
    val coord: Coord = Coord(),
    @Json(name = "country")
    val country: String = "",
    @Json(name = "id")
    val id: Int = 0,
    @Json(name = "name")
    val name: String = "",
    @Json(name = "population")
    val population: Int = 0,
    @Json(name = "sunrise")
    val sunrise: Int = 0,
    @Json(name = "sunset")
    val sunset: Int = 0,
    @Json(name = "timezone")
    val timezone: Int = 0
) : Parcelable