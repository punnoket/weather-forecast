package com.pannawat.weatherforecast.constant

import com.pannawat.weatherforecast.R

enum class UnitEnum(val title: Int, val value: String) {

    CELSIUS(
        R.string.celsius_unit, "metric"
    ),
    FAHRENHEIT(
        R.string.fahrenheit_unit, "imperial"
    );
}
