package com.pannawat.weatherforecast.network.model.weather


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Keep
@JsonClass(generateAdapter = true)
@Parcelize
data class Weather(
    @Json(name = "description")
    val description: String = "",
    @Json(name = "icon")
    val icon: String = "",
    @Json(name = "id")
    val id: Int = 0,
    @Json(name = "main")
    val main: String = ""
) : Parcelable