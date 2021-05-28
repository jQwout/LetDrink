package io.letDrink.localbar.db.dao

import androidx.room.*
import io.letDrink.localbar.db.entity.Cocktail
import io.letDrink.localbar.db.entity.CocktailWithIngredients

@Dao
internal abstract class CocktailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun add(list: List<Cocktail>)

    @Query("SELECT * FROM `Cocktail` WHERE name == :name")
    abstract suspend fun getByName(name: String): List<CocktailWithIngredients>

    @Query("SELECT * FROM `Cocktail` WHERE keywords LIKE '%' || :key  || '%'")
    abstract suspend fun getByKey(key: String): List<CocktailWithIngredients>

    @Query("SELECT * FROM `Cocktail` WHERE remoteId == :id LIMIT 1")
    abstract suspend fun getById(id: Long): CocktailWithIngredients?

    @Query("SELECT * FROM `Cocktail` ORDER BY RANDOM() LIMIT 1")
    abstract suspend fun getRandom(): CocktailWithIngredients

    @Query("SELECT * FROM `Cocktail` WHERE localFileName LIKE '%' || :local  || '%'")
    abstract suspend fun getLocalName(local: String): List<CocktailWithIngredients>
}

