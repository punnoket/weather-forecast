package com.pannawat.weatherforecast.network.model.forecast

import android.os.Parcelable
import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Keep
@JsonClass(generateAdapter = true)
@Parcelize
data class Sys(
    @Json(name = "pod")
    val pod: String = ""
) : Parcelable
