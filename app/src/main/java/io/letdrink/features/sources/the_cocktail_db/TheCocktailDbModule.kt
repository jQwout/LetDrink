package io.letdrink.features.sources.the_cocktail_db

import com.example.thecocktaildb.network.CocktailApiServiceLocator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object TheCocktailDbModule {

    @Provides
    @Singleton
    fun provideCocktailApiServiceLocator(okHttpClient: OkHttpClient): CocktailApiServiceLocator {
        return CocktailApiServiceLocator(okHttpClient)
    }

    @Provides
    fun providerSourceContract(locator: CocktailApiServiceLocator): NetworkSourceContract {
        return NetworkSourceContract(locator.api)
    }
}