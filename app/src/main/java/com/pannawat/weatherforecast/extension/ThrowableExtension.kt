package com.pannawat.weatherforecast.extension

import android.annotation.SuppressLint
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import kotlinx.parcelize.Parcelize
import retrofit2.HttpException

@JsonClass(generateAdapter = true)
@Parcelize
data class ErrorResponse(
    @Json(name = "message")
    val message: String = "",
    @Json(name = "code")
    val code: String = ""
) : Parcelable

@SuppressLint("CheckResult")
fun Throwable.getErrorResponse(): ErrorResponse {
    val defaultError = ErrorResponse(
        message = this.message.toString(),
        code = ""
    )
    return try {
        return when (this) {
            is HttpException -> {
                val errorBody = this.response()?.errorBody()?.string()
                errorBody?.let {
                    val moshi = Moshi.Builder().build()
                    val jsonAdapter: JsonAdapter<ErrorResponse> =
                        moshi.adapter(ErrorResponse::class.java)
                    jsonAdapter.fromJson(errorBody) ?: defaultError
                } ?: run {
                    defaultError
                }
            }
            else -> {
                defaultError
            }
        }
    } catch (e: Exception) {
        defaultError
    }
}
