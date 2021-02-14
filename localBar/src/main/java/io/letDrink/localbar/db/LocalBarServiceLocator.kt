package io.letDrink.localbar.db

import android.content.Context
import com.google.gson.Gson

class LocalBarServiceLocator(context: Context) {

    private val db: LocalBarDB = PrepopulateDBExecutor(context).localBarDB

    val repository: CocktailRepository by lazy {
        CocktailRepository(db.cocktailDao, db.ingredientDao)
    }

    val cocktailConsumer: CocktailConsumer by lazy {
        CocktailConsumer(db.ingredientDao, db.cocktailDao, db.cocktailWithIngredientDao)
    }

    val favouritesRepository: FavouritesRepository by lazy {
        FavouritesRepository(db.favouritesDao)
    }
}