package com.pannawat.weatherforecast.provider

import com.pannawat.weatherforecast.network.interceptor.BasicAuthentication
import com.pannawat.weatherforecast.network.service.WeatherService
import com.serjltt.moshi.adapters.DeserializeOnly
import com.serjltt.moshi.adapters.SerializeOnly
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class AppNetworkingProvider(
    endpoint: String,
    okHttpClient: OkHttpClient.Builder,
    rxJava3CallAdapterFactory: RxJava3CallAdapterFactory = RxJava3CallAdapterFactory.createSynchronous()
) {

    private val retrofit: Retrofit
    val weatherService: WeatherService

    init {
        val moshiConvertFactory = MoshiConverterFactory.create(
            Moshi.Builder().add(DeserializeOnly.ADAPTER_FACTORY).add(SerializeOnly.ADAPTER_FACTORY)
                .build()
        )

        okHttpClient.addInterceptor(BasicAuthentication())
        retrofit = Retrofit.Builder().baseUrl(endpoint).addConverterFactory(moshiConvertFactory)
            .addCallAdapterFactory(rxJava3CallAdapterFactory).client(okHttpClient.build()).build()

        weatherService = retrofit.create(WeatherService::class.java)
    }
}
