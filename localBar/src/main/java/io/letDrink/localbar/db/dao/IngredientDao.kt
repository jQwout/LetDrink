package io.letDrink.localbar.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.letDrink.localbar.db.entity.Ingredient
import io.letDrink.localbar.db.entity.IngredientsWithCocktails

@Dao
internal abstract class IngredientDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun add(ingredient: List<Ingredient>)

    @Query("SELECT * FROM `Ingredient` WHERE ingredient == :ingredient")
    abstract suspend fun getByIngredient(ingredient: String): List<IngredientsWithCocktails>
}