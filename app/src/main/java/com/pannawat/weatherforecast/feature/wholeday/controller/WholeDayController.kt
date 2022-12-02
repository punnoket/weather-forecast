package com.pannawat.weatherforecast.feature.wholeday.controller

import com.airbnb.epoxy.TypedEpoxyController
import com.pannawat.weatherforecast.feature.wholeday.controller.epoxymodel.cityName
import com.pannawat.weatherforecast.feature.wholeday.controller.epoxymodel.forecast
import com.pannawat.weatherforecast.feature.wholeday.model.WholeDayViewState
import javax.inject.Inject

class WholeDayController @Inject constructor() : TypedEpoxyController<WholeDayViewState>() {
    companion object {
        @JvmStatic
        private val TAG = WholeDayController::class.java.simpleName
    }

    override fun buildModels(data: WholeDayViewState?) {
        data?.run {
            forecastResponse?.apply {

                cityName {
                    id(city.name)
                    cityName(city.name)
                }

                list.forEach { forecast ->
                    forecast {
                        id(forecast.dt)
                        forecast(forecast)
                        unit(unit)
                    }
                }
            }
        }
    }
}
