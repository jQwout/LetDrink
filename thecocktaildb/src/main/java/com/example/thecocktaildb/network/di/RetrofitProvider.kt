package io.letdrink.common.network.di

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitProvider {

    fun provideRetrofit(okHttp: OkHttpClient ,factory: GsonFactory, url: String): Retrofit {
        return Retrofit.Builder()
            .client(okHttp)
            .addConverterFactory(GsonConverterFactory.create(factory.gson))
            .baseUrl(url)
            .build()
    }
}