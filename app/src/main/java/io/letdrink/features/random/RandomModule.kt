package io.letdrink.features.random

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import io.letDrink.localbar.db.CocktailConsumer
import io.letDrink.localbar.db.CocktailRepository

@Module
@InstallIn(ActivityComponent::class)
object RandomModule {

    @Provides
    fun service(
        sourceWrapper: SourceWrapper,
        favoritesRepository: CocktailRepository,
        cocktailConsumer: CocktailConsumer
    ): RandomCocktailService {
        return RandomCocktailService(sourceWrapper, favoritesRepository, cocktailConsumer)
    }

}