package com.pannawat.weatherforecast.service

import com.pannawat.weatherforecast.Util.enqueueMockResponse
import com.pannawat.weatherforecast.Util.okHttpClient
import com.pannawat.weatherforecast.network.interceptor.BasicAuthentication
import com.pannawat.weatherforecast.network.service.ForecastService
import com.serjltt.moshi.adapters.DeserializeOnly
import com.serjltt.moshi.adapters.SerializeOnly
import com.squareup.moshi.Moshi
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

@RunWith(MockitoJUnitRunner::class)
class ForecastServiceTest {

    private lateinit var forecastService: ForecastService
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        forecastService = Retrofit.Builder().baseUrl(mockWebServer.url("/")).addConverterFactory(
            MoshiConverterFactory.create(
                Moshi.Builder().add(DeserializeOnly.ADAPTER_FACTORY)
                    .add(SerializeOnly.ADAPTER_FACTORY).build()
            )
        ).client(okHttpClient().addInterceptor(BasicAuthentication()).build())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.createSynchronous()).build()
            .create(ForecastService::class.java)
    }

    @Test
    fun `getForecast when success`() {
        // Arrange
        enqueueMockResponse("loadForecast_whenSuccess.json", mockWebServer)
        val query = mapOf("q" to "London", "units" to "metric", "cnt" to "3")

        // Act & Assert
        forecastService.getForecast(query).test().assertComplete().assertNoErrors()
            .assertValue { result ->
                result.city.name == "London"
            }.assertValue { result ->
                result.list.size == 3
            }

        val request = mockWebServer.takeRequest()

        MatcherAssert.assertThat(
            request.path,
            CoreMatchers.`is`("/forecast?q=London&units=metric&cnt=3&appid=7b0ec359fa40d6cfb43ae8f5c0e7d141")
        )
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }
}
