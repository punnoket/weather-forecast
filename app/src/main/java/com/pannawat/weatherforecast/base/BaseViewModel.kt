package com.pannawat.weatherforecast.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {
    protected val disposables by lazy { CompositeDisposable() }
    val errorMessageLiveData by lazy { MutableLiveData<Throwable>() }
    val loadingLiveData by lazy { MutableLiveData<Boolean>() }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }

    protected fun showLoading() {
        loadingLiveData.postValue(true)
    }

    protected fun hideLoading() {
        loadingLiveData.postValue(false)
    }

    protected fun showError(errorMessage: Throwable) {
        errorMessageLiveData.postValue(errorMessage)
    }
}
