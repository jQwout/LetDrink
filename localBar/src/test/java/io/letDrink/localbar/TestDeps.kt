package io.letDrink.localbar

import com.google.gson.Gson
import io.letDrink.localbar.db.github.OpenDrinkGithubApi
import io.letDrink.localbar.db.storage.CocktailsRemoteStorage
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

object TestDeps {

    private val okHttpClient = OkHttpClient().newBuilder().apply {
        connectTimeout(10, TimeUnit.SECONDS)
        readTimeout(10, TimeUnit.SECONDS)
        writeTimeout(10, TimeUnit.SECONDS)
    }
        .build()

    val gson = Gson()


    val api = Retrofit.Builder().baseUrl(OpenDrinkGithubApi.RAW_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(OpenDrinkGithubApi::class.java)


    val cocktailsRemoteStorage = CocktailsRemoteStorage(api)


    val recDir = File("E:\\pragma\\letDrink\\common\\recipes")
}