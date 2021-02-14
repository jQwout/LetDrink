package io.letdrink.features.random

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import io.letDrink.localbar.db.CocktailConsumer
import io.letDrink.localbar.db.CocktailRepository
import io.letdrink.features.ingredient.FindByIngredientUseCase
import io.letdrink.features.sources.SourceWrapper

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