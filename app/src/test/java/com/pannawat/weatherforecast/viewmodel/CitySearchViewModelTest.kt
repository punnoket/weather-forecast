package com.pannawat.weatherforecast.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.pannawat.weatherforecast.TestSchedulersProvider
import com.pannawat.weatherforecast.Util.getValue
import com.pannawat.weatherforecast.Util.mockWeatherResponse
import com.pannawat.weatherforecast.constant.UnitEnum
import com.pannawat.weatherforecast.feature.search.CitySearchViewModel
import com.pannawat.weatherforecast.feature.search.usecase.SearchWeatherUseCase
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class CitySearchViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var searchWeatherUseCase: SearchWeatherUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun `searchWeather when success`() {
        // Arrange
        val weatherResponse = mockWeatherResponse()
        whenever(
            searchWeatherUseCase.execute(
                "london", "metric"
            )
        ).thenReturn(Single.just(weatherResponse))

        // Act & Assert
        val viewModel = CitySearchViewModel(searchWeatherUseCase, TestSchedulersProvider())
        viewModel.searchWeather("london", UnitEnum.CELSIUS)

        val citySearchViewState = getValue(
            viewModel.citySearchViewState
        )

        Assert.assertEquals(citySearchViewState?.weatherResponse, weatherResponse)
        Assert.assertEquals(citySearchViewState?.unit, UnitEnum.CELSIUS)
    }
}