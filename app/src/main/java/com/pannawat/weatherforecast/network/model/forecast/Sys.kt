package com.pannawat.weatherforecast.network.model.forecast


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Keep
@JsonClass(generateAdapter = true)
@Parcelize
data class Sys(
    @Json(name = "pod")
    val pod: String = ""
) : Parcelable