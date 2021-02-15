package com.example.thecocktaildb.network.di

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitProvider {

    fun provideRetrofit(okHttp: OkHttpClient, factory: GsonFactory, url: String): Retrofit {
        return Retrofit.Builder()
            .client(okHttp)
            .addConverterFactory(GsonConverterFactory.create(factory.gson))
            .baseUrl(url)
            .build()
    }
}