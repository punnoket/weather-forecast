package com.pannawat.weatherforecast.feature.wholeday

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.pannawat.weatherforecast.base.BaseViewModel
import com.pannawat.weatherforecast.constant.UnitEnum
import com.pannawat.weatherforecast.feature.wholeday.model.WholeDayViewState
import com.pannawat.weatherforecast.feature.wholeday.usecase.LoadForecastUseCase
import com.pannawat.weatherforecast.network.model.forecast.ForecastResponse
import com.pannawat.weatherforecast.provider.SchedulersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

@HiltViewModel
class WholeDayViewModel @Inject constructor(
    private val loadForecastUseCase: LoadForecastUseCase,
    private val schedulersProvider: SchedulersProvider,
) : BaseViewModel() {
    companion object {
        @JvmStatic
        private val TAG = WholeDayViewModel::class.java.simpleName
    }

    // Args
    lateinit var args: WholeDayFragmentArgs

    // View State
    val wholeDayViewState by lazy {
        MediatorLiveData<WholeDayViewState>().apply {
            addSource(forecastResponseLiveData) { source ->
                value = value?.copy(
                    forecastResponse = source
                ) ?: WholeDayViewState(forecastResponse = source)
            }
            addSource(unitLiveData) { source ->
                value = value?.copy(
                    unit = source
                ) ?: WholeDayViewState(unit = source)
            }
        }
    }

    // Live Data
    private val forecastResponseLiveData by lazy { MutableLiveData<ForecastResponse>() }
    private val unitLiveData by lazy { MutableLiveData<UnitEnum>() }

    fun searchWeather(
        unitEnum: UnitEnum
    ) {
        disposables += loadForecastUseCase.execute(
            args.cityName,
            unitEnum.value
        ).subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())
            .doOnSubscribe {
                showLoading()
            }.doOnEvent { _, _ ->
                hideLoading()
            }.doOnError {
                showError(it)
            }.subscribeBy(
                onError = {},
                onSuccess = {
                    forecastResponseLiveData.value = it
                    unitLiveData.value = unitEnum
                }
            )
    }
}
