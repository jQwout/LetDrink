package io.letDrink.localbar.db

import io.letDrink.localbar.db.dao.CocktailDao
import io.letDrink.localbar.db.dao.CocktailWithIngredientDao
import io.letDrink.localbar.db.dao.IngredientDao
import io.letDrink.localbar.db.entity.Cocktail
import io.letDrink.localbar.db.entity.CocktailIngredientCrossRef
import io.letDrink.localbar.db.entity.CocktailWithIngredients
import io.letDrink.localbar.db.entity.Ingredient

class CocktailConsumer internal constructor(
    private val ingredientDao: IngredientDao,
    private val cocktailDao: CocktailDao,
    private val cocktailWithIngredientDao: CocktailWithIngredientDao
) {
    suspend fun add(cocktail: Cocktail, ingredient: List<Ingredient>) {
        if (cocktailDao.getByName(cocktail.name).isNotEmpty()) return
        cocktailDao.add(listOf(cocktail))
        ingredientDao.add(ingredient)
        for (i in ingredient) {
            cocktailWithIngredientDao.add(CocktailIngredientCrossRef(cocktail.name, i.ingredient))
        }
    }
}