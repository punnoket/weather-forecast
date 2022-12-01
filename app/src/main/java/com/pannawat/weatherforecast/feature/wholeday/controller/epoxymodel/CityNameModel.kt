package com.pannawat.weatherforecast.feature.wholeday.controller.epoxymodel

import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.google.android.material.textview.MaterialTextView
import com.pannawat.weatherforecast.R
import com.pannawat.weatherforecast.base.KotlinEpoxyHolder
import com.pannawat.weatherforecast.constant.UnitEnum
import com.pannawat.weatherforecast.network.model.forecast.Forecast

@EpoxyModelClass(layout = R.layout.list_item_city_name)
abstract class CityNameModel : EpoxyModelWithHolder<CityNameModel.Holder>() {

    @EpoxyAttribute
    lateinit var cityName: String

    override fun bind(holder: Holder) {
        with(holder) {
            textViewCityName.text = cityName
        }
    }

    class Holder : KotlinEpoxyHolder() {
        val textViewCityName by bind<MaterialTextView>(R.id.textViewCityName)
    }
}