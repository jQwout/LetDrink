package io.letDrink.localbar.db

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import io.letDrink.localbar.db.github.OpenDrinkGithubApi
import io.letDrink.localbar.db.github.OpenDrinkGithubApi.Companion.RAW_URL
import io.letDrink.localbar.db.repository.*
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

    private val api: OpenDrinkGithubApi by lazy {
        Retrofit.Builder().baseUrl(RAW_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(OpenDrinkGithubApi::class.java)
    }

    val cocktailRepository: CocktailRepository by lazy {
        CocktailRepository()
    }

    val favouritesRepository: FavouritesRepository by lazy {
        FavouritesRepository(sharedPreferences)
    }

    private val featuredRepository: FeaturedRepository by lazy {
        FeaturedRepository(api)
    }

    val localStorage: CocktailsLocalStorage by lazy {
        CocktailsLocalStorage(
            File(context.cacheDir, "rec"), gson, context.assets
        )
    }

    val remoteStorage: CocktailsRemoteStorage by lazy {
        CocktailsRemoteStorage(api)
    }


    val populateRepositoryUseCase by lazy {
        PopulateRepositoryUseCase(cocktailRepository, localStorage)
    }

    val updateUseCase: CheckUpdateUseCase by lazy {
        CheckUpdateUseCase(
            localStorage,
            remoteStorage,
            sharedPreferences,
            populateRepositoryUseCase
        )
    }

    val cocktailFacade: CocktailFacade by lazy {
        CocktailFacade(cocktailRepository, favouritesRepository)
    }

    val featuredFacade: FeaturedFacade by lazy {
        FeaturedFacade(featuredRepository, localStorage, remoteStorage, favouritesRepository)
    }

    val categoryRepository: CategoryRepository by lazy {
        CategoryRepository(cocktailRepository)
    }

}