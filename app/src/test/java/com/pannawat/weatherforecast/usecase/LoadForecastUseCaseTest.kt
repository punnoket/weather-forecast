package com.pannawat.weatherforecast.usecase

import com.pannawat.weatherforecast.Util.mockForecastResponse
import com.pannawat.weatherforecast.feature.wholeday.usecase.LoadForecastUseCase
import com.pannawat.weatherforecast.network.repository.ForecastRepository
import io.reactivex.rxjava3.core.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class LoadForecastUseCaseTest {

    @Mock
    lateinit var forecastRepository: ForecastRepository

    @InjectMocks
    lateinit var loadForecastUseCase: LoadForecastUseCase

    @Test
    fun `loadForecast when success`() {
        // Arrange
        whenever(forecastRepository.getForecast(any())).doReturn(
            Single.just(
                mockForecastResponse()
            )
        )

        // Act & Assert
        loadForecastUseCase.execute("london", "metric").test().assertNoErrors().assertComplete()
            .assertValue { result ->
                result.city.name == "London"
            }.assertValue { result ->
                result.list.size == 3
            }
    }
}
