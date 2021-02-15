package com.example.thecocktaildb.network.di

import com.example.thecocktaildb.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object OkHttpFactory {

    private const val HTTP_TIMEOUT_MS = 5000

    fun createOkHttp(): OkHttpClient {
        val builder = OkHttpClient.Builder().also {
            it.connectTimeout(HTTP_TIMEOUT_MS.toLong(), TimeUnit.MILLISECONDS)
            it.readTimeout(HTTP_TIMEOUT_MS.toLong(), TimeUnit.MILLISECONDS)
            it.writeTimeout(HTTP_TIMEOUT_MS.toLong(), TimeUnit.MILLISECONDS)
        }

        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            builder.addInterceptor(interceptor)
        }

        return builder.build()
    }
}