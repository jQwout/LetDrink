package io.letDrink.localbar.db

import io.letDrink.localbar.db.dao.FavouritesDao
import io.letDrink.localbar.db.entity.Cocktail
import io.letDrink.localbar.db.entity.CocktailWithIngredients
import kotlinx.coroutines.flow.Flow

class FavouritesRepository internal constructor(
    private val favouritesDao: FavouritesDao
) {
    suspend fun add(c: Cocktail) {
        favouritesDao.changeFavourite(c, true)
    }

    suspend fun remove(c: Cocktail) {
        favouritesDao.changeFavourite(c, false)
    }

    fun observeFavourites(): Flow<List<CocktailWithIngredients>> {
        return favouritesDao.observeFavourites(true)
    }
}