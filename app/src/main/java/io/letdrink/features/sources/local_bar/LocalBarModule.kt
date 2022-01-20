package io.letdrink.features.sources.local_bar

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.letDrink.localbar.db.LocalBarServiceLocator
import io.letDrink.localbar.db.repository.*
import io.letDrink.localbar.db.usecase.CheckUpdateUseCase
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalBarModule {

    @Provides
    @Singleton
    fun provideLocalBar(
        @ApplicationContext context: Context,
        sharedPreferences: SharedPreferences,
        okHttpClient: OkHttpClient
    ): LocalBarServiceLocator {
        return LocalBarServiceLocator(
            context,
            sharedPreferences,
            okHttpClient,
            Gson()
        )
    }

    @Provides
    fun provideCocktailRepository(locator: LocalBarServiceLocator): CocktailRepository {
        return locator.cocktailRepository
    }


    @Provides
    fun provideFavouritesRepository(locator: LocalBarServiceLocator): FavouritesRepository {
        return locator.favouritesRepository
    }

    @Provides
    fun provideCocktailFacade(locator: LocalBarServiceLocator): CocktailFacade {
        return locator.cocktailFacade
    }

    @Provides
    fun provideCheckUpdateUseCase(locator: LocalBarServiceLocator): CheckUpdateUseCase {
        return locator.updateUseCase
    }

    @Provides
    fun provideFeaturedFacade(locator: LocalBarServiceLocator): FeaturedFacade {
        return locator.featuredFacade
    }

    @Provides
    fun provideCategoryRepository(locator: LocalBarServiceLocator): CategoryRepository {
        return locator.categoryRepository
    }
}