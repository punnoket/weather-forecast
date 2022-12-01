package com.pannawat.weatherforecast.feature.wholeday

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pannawat.weatherforecast.R
import com.pannawat.weatherforecast.base.BaseFragment
import com.pannawat.weatherforecast.constant.UnitEnum
import com.pannawat.weatherforecast.databinding.FragmentWholeDayBinding
import com.pannawat.weatherforecast.extension.viewBinding
import com.pannawat.weatherforecast.feature.wholeday.controller.WholeDayController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class WholeDayFragment : BaseFragment(R.layout.fragment_whole_day) {

    @Inject
    lateinit var wholeDayController: WholeDayController

    // Data Members
    private val binding by viewBinding(FragmentWholeDayBinding::bind)
    private val wholeDayViewModel: WholeDayViewModel by viewModels()

    // Args
    private val args by navArgs<WholeDayFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        wholeDayViewModel.args = args
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        wholeDayViewModel.searchWeather(args.unit)
    }

    private fun initView() {
        with(binding) {

            recyclerView.apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                adapter = wholeDayController.adapter
                addItemDecoration(
                    DividerItemDecoration(
                        recyclerView.context, DividerItemDecoration.VERTICAL
                    )
                )
            }

            wholeDayViewModel.wholeDayViewState.observe(viewLifecycleOwner) { state ->
                wholeDayController.setData(state)
            }


            with(viewUnitButtonGroup) {
                toggleButtonGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
                    if (isChecked) {
                        when (checkedId) {
                            R.id.buttonCelsius -> {
                                wholeDayViewModel.searchWeather(UnitEnum.CELSIUS)
                            }
                            R.id.buttonFahrenheit -> {
                                wholeDayViewModel.searchWeather(UnitEnum.FAHRENHEIT)
                            }
                        }
                    }
                }

                when (args.unit) {
                    UnitEnum.CELSIUS -> {
                        buttonCelsius.isChecked = true
                    }
                    UnitEnum.FAHRENHEIT -> {
                        buttonFahrenheit.isChecked = true
                    }
                }
            }
        }
    }

    override val viewModelList: List<ViewModel>
        get() = super.viewModelList.toMutableList().apply {
            add(wholeDayViewModel)
        }

}