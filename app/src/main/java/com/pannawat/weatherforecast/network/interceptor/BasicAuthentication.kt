package com.pannawat.weatherforecast.network.interceptor

import com.pannawat.weatherforecast.BuildConfig
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class BasicAuthentication : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        val url: HttpUrl =
            request.url.newBuilder().addQueryParameter("appid", BuildConfig.APP_ID).build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}
