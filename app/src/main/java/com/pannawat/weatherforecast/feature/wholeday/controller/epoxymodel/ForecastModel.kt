package com.pannawat.weatherforecast.feature.wholeday.controller.epoxymodel

import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.google.android.material.textview.MaterialTextView
import com.pannawat.weatherforecast.R
import com.pannawat.weatherforecast.base.KotlinEpoxyHolder
import com.pannawat.weatherforecast.constant.UnitEnum
import com.pannawat.weatherforecast.network.model.forecast.Forecast

@EpoxyModelClass(layout = R.layout.list_item_forecast)
abstract class ForecastModel : EpoxyModelWithHolder<ForecastModel.Holder>() {

    @EpoxyAttribute
    lateinit var forecast: Forecast

    @EpoxyAttribute
    lateinit var unit: UnitEnum

    override fun bind(holder: Holder) {
        with(holder) {
            with(forecast) {
                textViewDate.text = dtTxt
                textViewHumidity.text = textViewHumidity.context.getString(
                    R.string.humidityValue, main.humidity.toString()
                )
                textViewTemperature.text =
                    textViewTemperature.context.getString(unit.title, main.temp.toInt())

            }
        }
    }

	class Holder : KotlinEpoxyHolder() {
		val textViewDate by bind<MaterialTextView>(R.id.textViewDate)
		val textViewHumidity by bind<MaterialTextView>(R.id.textViewHumidity)
		val textViewTemperature by bind<MaterialTextView>(R.id.textViewTemperature)
	}
}