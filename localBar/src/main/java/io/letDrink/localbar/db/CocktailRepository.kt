package io.letDrink.localbar.db

import io.letDrink.localbar.db.dao.CocktailDao
import io.letDrink.localbar.db.dao.IngredientDao
import io.letDrink.localbar.db.entity.Cocktail
import io.letDrink.localbar.db.entity.CocktailWithIngredients
import io.letDrink.localbar.db.entity.IngredientsWithCocktails

class CocktailRepository internal constructor(
    private val cocktailDao: CocktailDao,
    private val ingredientDao: IngredientDao
) {

    internal suspend fun add(list: List<Cocktail>) {
        cocktailDao.add(list)
    }

    internal suspend fun add(cocktail: Cocktail) {
        cocktailDao.add(listOf(cocktail))
    }

    suspend fun getByName(name: String): List<CocktailWithIngredients> {
        return cocktailDao.getByName(name)
    }

    suspend fun getByLocalName(name: String) : List<CocktailWithIngredients> {
        return cocktailDao.getLocalName(name)
    }

    suspend fun getByKey(key: String): List<CocktailWithIngredients> {
        return cocktailDao.getByKey(key)
    }

    suspend fun getByIngredients(ingredient: String): List<CocktailWithIngredients> {
        return ingredientDao.getByIngredient(ingredient).flatMap {
            it.cocktails.flatMap { cocktail ->
                cocktailDao.getByName(cocktail.name)
            }
        }
    }

    suspend fun getById(id: Long): CocktailWithIngredients? {
        return cocktailDao.getById(id)
    }

    suspend fun getRandom(): CocktailWithIngredients {
        return cocktailDao.getRandom()
    }
}

