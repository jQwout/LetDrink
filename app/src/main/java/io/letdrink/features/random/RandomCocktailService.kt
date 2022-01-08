package io.letdrink.features.random

import io.letDrink.localbar.db.CocktailConsumer
import io.letDrink.localbar.db.CocktailRepository


class RandomCocktailService(
    private val favoritesRepository: CocktailRepository,
    private val cocktailConsumer: CocktailConsumer
) {

    suspend fun get(): List<Drink> {
        return listOf(getRandom())
    }

    private suspend fun getRandom(): Drink {
        val drink = sourceWrapper.random(false)
        val localDrink = favoritesRepository.getByName(drink.drinkName).firstOrNull()
        if (localDrink == null) {
            cocktailConsumer.add(drink.toCocktail(), drink.ingredients.map { it.toIngredient() })
        }
        return favoritesRepository.getRandom().toDrink()
    }

    private val drink = Drink(
        id = 17194,
        drinkName = "White Lady",
        tags = "IBA,Classic",
        instructions = "Add all ingredients into cocktail shaker filled with ice. Shake well and strain into large cocktail glass.",
        ingredients = listOf(
            Drink.Ingredient("Gin", "4cl"),
            Drink.Ingredient("Triple Sec", "3cl"),
            Drink.Ingredient("Lemon Juice", "2cl")
        ),
        imageSource = null,
        drinkThumb = "https://www.thecocktaildb.com/images/media/drink/jofsaz1504352991.jpg",
        isFavourite = false,
        alcoholic = "Alcoholic",
        categories = "Ordinary Drink",
        description = null
    )
}

