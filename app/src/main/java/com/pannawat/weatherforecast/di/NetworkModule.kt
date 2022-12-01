package com.pannawat.weatherforecast.di

import com.pannawat.weatherforecast.BuildConfig
import com.pannawat.weatherforecast.provider.SchedulersProvider
import com.pannawat.weatherforecast.provider.AppNetworkingProvider
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
    fun provideSchedulersProvider(): SchedulersProvider = SchedulersProvider()

    @Provides
    fun provideMoshi() = Moshi.Builder()
        .add(DeserializeOnly.ADAPTER_FACTORY)
        .add(SerializeOnly.ADAPTER_FACTORY)
        .build()

    @Provides
    fun provideInterceptor() = HttpLoggingInterceptor()
        .apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

    @Provides
    fun provideOkHttpClient(
        interceptor: HttpLoggingInterceptor,
    ): OkHttpClient.Builder {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .callTimeout(60, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val chainBuilder = chain.request().newBuilder()
                val request = chainBuilder.build()
                chain.proceed(request)
            }

        return okHttpClient
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
