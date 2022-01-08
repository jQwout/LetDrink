package io.letDrink.localbar.db

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import io.letDrink.localbar.db.github.OpenDrinkGithubApi
import io.letDrink.localbar.db.github.OpenDrinkGithubApi.Companion.RAW_URL
import io.letDrink.localbar.db.repository.CocktailRepository
import io.letDrink.localbar.db.repository.FavouritesRepository
import io.letDrink.localbar.db.storage.CocktailsLocalStorage
import io.letDrink.localbar.db.storage.CocktailsRemoteStorage
import io.letDrink.localbar.db.usecase.CheckUpdateUseCase
import io.letDrink.localbar.db.usecase.PopulateRepositoryUseCase
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

class LocalBarServiceLocator(
    context: Context,
    sharedPreferences: SharedPreferences,
    okHttpClient: OkHttpClient,
    gson: Gson,
) {

    val cocktailRepository: CocktailRepository by lazy {
        CocktailRepository()
    }

    val favouritesRepository: FavouritesRepository by lazy {
        FavouritesRepository(cocktailRepository, sharedPreferences)
    }

    val localStorage: CocktailsLocalStorage by lazy {
        CocktailsLocalStorage(
            File(context.cacheDir, "rec"), gson
        )
    }

    val remoteStorage: CocktailsRemoteStorage by lazy {
        CocktailsRemoteStorage(
            Retrofit.Builder().baseUrl(RAW_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(OpenDrinkGithubApi::class.java)
        )
    }

    val updateUseCase: CheckUpdateUseCase by lazy {
        CheckUpdateUseCase(localStorage, remoteStorage, sharedPreferences)
    }

    val populateRepositoryUseCase by lazy {
        PopulateRepositoryUseCase(cocktailRepository, localStorage)
    }

}

object LOCAL_BAR_CONST {
    const val IMAGES =
        "https://raw.githubusercontent.com/alfg/opendrinks/master/src/assets/recipes/"
}