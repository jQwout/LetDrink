package io.letDrink.localbar.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.google.gson.Gson
import io.letDrink.localbar.db.dao.CocktailDao
import io.letDrink.localbar.db.dao.CocktailWithIngredientDao
import io.letDrink.localbar.db.dao.FavouritesDao
import io.letDrink.localbar.db.dao.IngredientDao
import io.letDrink.localbar.db.entity.Cocktail
import io.letDrink.localbar.db.entity.CocktailIngredientCrossRef
import io.letDrink.localbar.db.entity.Ingredient

@Database(
    entities = [Cocktail::class, Ingredient::class, CocktailIngredientCrossRef::class],
    version = 8
)
internal abstract class LocalBarDB : RoomDatabase() {
    abstract val cocktailDao: CocktailDao
    abstract val ingredientDao: IngredientDao
    abstract val cocktailWithIngredientDao: CocktailWithIngredientDao
    abstract val favouritesDao: FavouritesDao
}