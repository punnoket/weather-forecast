package com.pannawat.weatherforecast.provider

import android.os.Looper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

open class SchedulersProvider {
    open val io: Scheduler
        get() = Schedulers.io()

    open val computation: Scheduler
        get() = Schedulers.computation()

    open val ui: Scheduler
        get() = AndroidSchedulers.from(Looper.getMainLooper())
}
