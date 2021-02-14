package io.letDrink.localbar.db.dao

import androidx.room.*
import io.letDrink.localbar.db.entity.Cocktail
import io.letDrink.localbar.db.entity.CocktailWithIngredients
import kotlinx.coroutines.flow.Flow

@Dao
internal abstract class FavouritesDao {

    //@Query("UPDATE `Cocktail` SET isFavorite = :isFavorite WHERE name == :name")
    //abstract suspend fun change(name: String, isFavorite: Boolean)

    @Query("SELECT * FROM `Cocktail` WHERE isFavorite == :isFavorite")
    abstract fun observeFavourites(isFavorite: Boolean): Flow<List<CocktailWithIngredients>>

    @Query("SELECT * FROM `Cocktail` WHERE isFavorite == :isFavorite")
    abstract suspend fun getFavourites(isFavorite: Boolean): List<CocktailWithIngredients>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun replaceItem(cocktail: Cocktail)

    @Transaction
    open suspend fun changeFavourite(cocktail: Cocktail, isFavorite: Boolean) {
        replaceItem(cocktail.copy(isFavorite = isFavorite))
    }
}