package com.example.thecocktaildb.network

import com.example.thecocktaildb.network.di.GsonFactory
import com.example.thecocktaildb.network.di.OkHttpFactory
import com.example.thecocktaildb.network.di.RetrofitProvider
import okhttp3.OkHttpClient

class CocktailApiServiceLocator(private val okHttpClient: OkHttpClient) {

    constructor() : this(OkHttpFactory.createOkHttp())

    companion object {
        private const val API = "https://www.thecocktaildb.com/api/json/v1/1/"
    }

    val api: CocktailApi by lazy {
        RetrofitProvider
            .provideRetrofit(okHttpClient, GsonFactory(), API)
            .create(CocktailApi::class.java)
    }
}