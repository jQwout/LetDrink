package io.letdrink.features.sources.local_bar

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import io.letDrink.localbar.db.CocktailConsumer
import io.letDrink.localbar.db.CocktailRepository
import io.letDrink.localbar.db.FavouritesRepository
import io.letDrink.localbar.db.LocalBarServiceLocator
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object LocalBarModule {

    @Provides
    @Singleton
    fun provideLocalBar(@ApplicationContext context: Context): LocalBarServiceLocator {
        return LocalBarServiceLocator(context)
    }

    @Provides
    fun provideCocktailRepository(locator: LocalBarServiceLocator): CocktailRepository {
        return locator.repository
    }

    @Provides
    fun provideCocktailConsumer(localBarServiceLocator: LocalBarServiceLocator): CocktailConsumer {
        return localBarServiceLocator.cocktailConsumer
    }

    @Provides
    fun provideFavouritesRepository(locator: LocalBarServiceLocator): FavouritesRepository {
        return locator.favouritesRepository
    }

    @Provides
    fun providerSourceContract(cocktailRepository: CocktailRepository): LocalSourceContract {
        return LocalSourceContract(cocktailRepository)
    }
}