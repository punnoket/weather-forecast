package com.pannawat.weatherforecast.network.model.forecast


import android.os.Parcelable
import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Keep
@JsonClass(generateAdapter = true)
@Parcelize
data class Clouds(
    @Json(name = "all")
    val all: Int = 0
) : Parcelable