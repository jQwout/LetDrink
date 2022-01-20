package io.letdrink.features.random

import io.letDrink.localbar.db.pojo.CocktailDto
import io.letDrink.localbar.db.pojo.CocktailRaw
import io.letDrink.localbar.db.repository.CocktailFacade
import io.letDrink.localbar.db.repository.CocktailRepository
import io.letDrink.localbar.db.repository.FavouritesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject


class RandomCocktailService @Inject constructor(
    private val cocktailFacade: CocktailFacade
) {

    private val flow =
        cocktailFacade.get().shareIn(CoroutineScope(Dispatchers.IO), SharingStarted.Lazily, 1)

    fun get(): Flow<List<CocktailDto>> {
        return flow.map {
            listOf(
                it.random(),
                it.random(),
                it.random()
            )
        }
    }
}

