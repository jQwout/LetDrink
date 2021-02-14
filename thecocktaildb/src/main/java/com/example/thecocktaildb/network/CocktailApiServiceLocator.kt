package com.example.thecocktaildb.network

import io.letdrink.common.network.di.GsonFactory
import io.letdrink.common.network.di.RetrofitProvider
import io.letdrink.features.random.CocktailApi
import okhttp3.OkHttpClient

class CocktailApiServiceLocator(private val okHttpClient: OkHttpClient) {

    companion object {
        private const val API = "https://www.thecocktaildb.com/api/json/v1/1/"
    }

    val api: CocktailApi by lazy {
        RetrofitProvider
            .provideRetrofit(okHttpClient, GsonFactory(), API)
            .create(CocktailApi::class.java)
    }
}