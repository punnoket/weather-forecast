package com.pannawat.weatherforecast

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.pannawat.weatherforecast.network.model.forecast.ForecastResponse
import com.pannawat.weatherforecast.network.model.weather.WeatherResponse
import com.squareup.moshi.Moshi
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import retrofit2.HttpException
import retrofit2.Response
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

object Util {
    fun loadJson(path: String): String {
        val inputStream = this.javaClass.classLoader?.getResourceAsStream(path)
        return inputStream?.bufferedReader().use { it?.readText() } ?: ""
    }

    fun okHttpClient(): OkHttpClient.Builder {
        return OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS)
            .callTimeout(60, TimeUnit.SECONDS)
    }

    fun enqueueMockResponse(fileName: String, mockWebServer: MockWebServer) {
        javaClass.classLoader?.let {
            val inputStream = it.getResourceAsStream(fileName)
            val source = inputStream.source().buffer()
            val mockResponse = MockResponse()
            mockResponse.setBody(source.readString(Charsets.UTF_8))
            mockWebServer.enqueue(mockResponse)
        }
    }

    fun mockErrorResponse(code: Int, path: String): HttpException {
        return HttpException(
            Response.error<ResponseBody>(
                code, loadJson(path).toResponseBody("application/json".toMediaTypeOrNull())
            )
        )
    }

    @Throws(InterruptedException::class)
    fun <T> getValue(liveData: LiveData<T>): T? {
        var data: T? = null
        val latch = CountDownLatch(1)
        val observer = object : Observer<T> {
            override fun onChanged(o: T?) {
                data = o
                latch.countDown()
                liveData.removeObserver(this)
            }
        }
        liveData.observeForever(observer)
        latch.await(2, TimeUnit.SECONDS)

        return data
    }

    fun mockWeatherResponse(): WeatherResponse {
        val moshi = Moshi.Builder().build()
        return moshi.adapter(WeatherResponse::class.java)
            .fromJson(loadJson("loadWeather_whenSuccess.json")) ?: WeatherResponse()
    }

    fun mockForecastResponse(): ForecastResponse {
        val moshi = Moshi.Builder().build()
        return moshi.adapter(ForecastResponse::class.java)
            .fromJson(loadJson("loadForecast_whenSuccess.json")) ?: ForecastResponse()
    }
}
