package com.pannawat.weatherforecast.usecase

import com.pannawat.weatherforecast.Util.mockErrorResponse
import com.pannawat.weatherforecast.Util.mockWeatherResponse
import com.pannawat.weatherforecast.extension.getErrorResponse
import com.pannawat.weatherforecast.feature.search.usecase.SearchWeatherUseCase
import com.pannawat.weatherforecast.network.repository.WeatherRepository
import io.reactivex.rxjava3.core.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.whenever
import retrofit2.HttpException

@RunWith(MockitoJUnitRunner::class)
class SearchWeatherUseCaseTest {

    @Mock
    lateinit var weatherRepository: WeatherRepository

    @InjectMocks
    lateinit var searchWeatherUseCase: SearchWeatherUseCase

    @Test
    fun `searchWeather when success`() {
        // Arrange
        whenever(weatherRepository.getWeather(any())).doReturn(
            Single.just(
                mockWeatherResponse()
            )
        )

        // Act & Assert
        searchWeatherUseCase.execute("london", "metric").test().assertNoErrors().assertComplete()
            .assertValue { result ->
                result.name == "London"
            }.assertValue { result ->
                result.main.humidity == 91
            }.assertValue { result ->
                result.main.temp.toInt() == 43
            }
    }

    @Test
    fun `searchWeather when error`() {
        // Arrange
        whenever(weatherRepository.getWeather(any())).doReturn(
            Single.error(mockErrorResponse(404, "loadWeather_whenError.json"))
        )

        // Act & Assert
        searchWeatherUseCase.execute("london", "metric").test().assertNotComplete()
            .assertFailure(HttpException::class.java)
            .assertError {
                val errorResponse = it.getErrorResponse()
                errorResponse.code == "404" && errorResponse.message == "city not found"
            }
    }
}
