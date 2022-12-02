package com.pannawat.weatherforecast.network.model.forecast


import android.os.Parcelable
import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Keep
@JsonClass(generateAdapter = true)
@Parcelize
data class Forecast(
    @Json(name = "clouds")
    val clouds: Clouds = Clouds(),
    @Json(name = "dt")
    val dt: Int = 0,
    @Json(name = "dt_txt")
    val dtTxt: String = "",
    @Json(name = "main")
    val main: Main = Main(),
    @Json(name = "pop")
    val pop: Double = 0.0,
    @Json(name = "sys")
    val sys: Sys = Sys(),
    @Json(name = "visibility")
    val visibility: Int = 0,
    @Json(name = "weather")
    val weather: List<Weather> = listOf(),
    @Json(name = "wind")
    val wind: Wind = Wind()
) : Parcelable