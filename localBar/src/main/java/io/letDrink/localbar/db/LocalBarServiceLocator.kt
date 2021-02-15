package io.letDrink.localbar.db

import android.content.Context
import com.google.gson.Gson

class LocalBarServiceLocator(context: Context, name: String? = null) {

    private val db: LocalBarDB = PrepopulateDBExecutor(context, name).localBarDB

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

object LOCAL_BAR_CONST {
    const val IMAGES =
        "https://raw.githubusercontent.com/alfg/opendrinks/master/src/assets/recipes/"
    const val JSON = "https://raw.githubusercontent.com/alfg/opendrinks/master/src/recipes/"
}