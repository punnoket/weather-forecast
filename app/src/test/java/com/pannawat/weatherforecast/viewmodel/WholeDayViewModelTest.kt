package com.pannawat.weatherforecast.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.pannawat.weatherforecast.TestSchedulersProvider
import com.pannawat.weatherforecast.Util.getValue
import com.pannawat.weatherforecast.Util.mockForecastResponse
import com.pannawat.weatherforecast.constant.UnitEnum
import com.pannawat.weatherforecast.feature.wholeday.WholeDayFragmentArgs
import com.pannawat.weatherforecast.feature.wholeday.WholeDayViewModel
import com.pannawat.weatherforecast.feature.wholeday.usecase.LoadForecastUseCase
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class WholeDayViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var loadForecastUseCase: LoadForecastUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun `loadForecast when success`() {
        // Arrange
        val forecastResponse = mockForecastResponse()
        whenever(loadForecastUseCase.execute("london", "metric")).doReturn(
            Single.just(
                forecastResponse
            )
        )

        // Act & Assert
        val viewModel = WholeDayViewModel(loadForecastUseCase, TestSchedulersProvider())
        viewModel.args = WholeDayFragmentArgs("london", UnitEnum.CELSIUS)
        viewModel.searchWeather(UnitEnum.CELSIUS)

        val citySearchViewState = getValue(
            viewModel.wholeDayViewState
        )

        Assert.assertEquals(citySearchViewState?.forecastResponse, forecastResponse)
        Assert.assertEquals(citySearchViewState?.unit, UnitEnum.CELSIUS)
    }
}