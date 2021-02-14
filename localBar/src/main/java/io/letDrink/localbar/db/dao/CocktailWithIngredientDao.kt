package io.letDrink.localbar.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import io.letDrink.localbar.db.entity.CocktailIngredientCrossRef

@Dao
internal abstract class CocktailWithIngredientDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun add(cocktailAndIngredient: CocktailIngredientCrossRef)

}