package com.pannawat.weatherforecast.di

import com.pannawat.weatherforecast.BuildConfig
import com.pannawat.weatherforecast.provider.SchedulersProvider
import com.pannawat.weatherforecast.provider.AppNetworkingProvider
import com.pannawat.weatherforecast.provider.AppSchedulersProvider
import com.serjltt.moshi.adapters.DeserializeOnly
import com.serjltt.moshi.adapters.SerializeOnly
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    fun provideSchedulersProvider(): SchedulersProvider = AppSchedulersProvider()

    @Provides
    fun provideMoshi() = Moshi.Builder()
        .add(DeserializeOnly.ADAPTER_FACTORY)
        .add(SerializeOnly.ADAPTER_FACTORY)
        .build()

    @Provides
    fun provideOkHttpClient(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .callTimeout(60, TimeUnit.SECONDS)
    }

    @Provides
    fun provideAppNetworkingProvider(
        okHttpClient: OkHttpClient.Builder
    ): AppNetworkingProvider {
        return AppNetworkingProvider(
            BuildConfig.BASE_URL,
            okHttpClient
        )
    }
}
