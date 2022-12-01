package com.pannawat.weatherforecast.feature.search

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.jakewharton.rxbinding4.view.clicks
import com.pannawat.weatherforecast.R
import com.pannawat.weatherforecast.base.BaseFragment
import com.pannawat.weatherforecast.constant.UnitEnum
import com.pannawat.weatherforecast.databinding.FragmentCitySearchBinding
import com.pannawat.weatherforecast.extension.viewBinding
import com.pannawat.weatherforecast.feature.search.model.CitySearchViewState
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.kotlin.subscribeBy

@AndroidEntryPoint
class CitySearchFragment : BaseFragment(R.layout.fragment_city_search) {

    // Data Members
    private val binding by viewBinding(FragmentCitySearchBinding::bind)
    private val citySearchViewModel: CitySearchViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        with(binding) {
            citySearchViewModel.citySearchViewState.observe(viewLifecycleOwner) { state ->
                    setContent(state)
                }

            subscriptions += buttonSearch.clicks().subscribeBy(onError = {}, onNext = {
                    citySearchViewModel.searchWeather(
                        editText.text.toString(),
                        citySearchViewModel.unitLiveData.value ?: UnitEnum.CELSIUS
                    )
                })

            subscriptions += buttonSeeWholeDay.clicks().subscribeBy(onError = {}, onNext = {
                    findNavController().navigate(
                        CitySearchFragmentDirections.actionToWholeDayFragment(
                            editText.text.toString(),
                            citySearchViewModel.citySearchViewState.value?.unit ?: UnitEnum.CELSIUS
                        )
                    )
                })

            with(viewUnitButtonGroup) {
                buttonCelsius.isChecked = true
                toggleButtonGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
                    if (isChecked) {
                        when (checkedId) {
                            R.id.buttonCelsius -> {
                                citySearchViewModel.searchWeather(
                                    editText.text.toString(), UnitEnum.CELSIUS
                                )
                            }
                            R.id.buttonFahrenheit -> {
                                citySearchViewModel.searchWeather(
                                    editText.text.toString(), UnitEnum.FAHRENHEIT
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setContent(citySearchViewState: CitySearchViewState) {
        with(binding) {
            citySearchViewState.weatherResponse?.apply {
                groupContent.isVisible = true
                textViewCityName.text = name
                textViewTemperature.text =
                    getString(citySearchViewState.unit.title, main.temp.toInt())
                textViewHumidity.text = getString(R.string.humidityValue, main.humidity.toString())

            } ?: run {
                groupContent.isVisible = false
            }
        }
    }

    override val viewModelList: List<ViewModel>
        get() = super.viewModelList.toMutableList().apply {
            add(citySearchViewModel)
        }

}