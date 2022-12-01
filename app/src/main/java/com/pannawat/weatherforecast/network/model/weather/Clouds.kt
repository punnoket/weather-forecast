package com.pannawat.weatherforecast.network.model.weather


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Keep
@JsonClass(generateAdapter = true)
@Parcelize
data class Clouds(
    @Json(name = "all")
    val all: Int = 0
) : Parcelable