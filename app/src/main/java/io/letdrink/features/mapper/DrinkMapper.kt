package io.letdrink.features.mapper

import io.letDrink.localbar.db.entity.Cocktail
import io.letDrink.localbar.db.entity.CocktailWithIngredients
import io.letDrink.localbar.db.entity.Ingredient
import com.example.thecocktaildb.network.models.Drink

fun CocktailWithIngredients.toDrink(): Drink {
    val ingredients = this.ingredients
    with(this.cocktail) {
        return Drink(
            id = this.remoteId,
            drinkName = this.name,
            tags = this.keywords.reduce { acc, s -> acc + s },
            instructions = this.directions.reduce { acc, s -> acc + s },
            imageSource = null,
            drinkThumb = this.image,
            isFavourite = this.isFavorite,
            alcoholic = "null",
            categories = null,
            description = this.description,
            ingredients = ingredients.map {
                Drink.Ingredient(it.ingredient, it.count)
            }
        )
    }
}

fun Drink.toCocktail(): Cocktail {
    return Cocktail(
        description = "",
        directions = listOf(this.instructions),
        github = "",
        image = this.getImage(),
        keywords = listOf(this.ingredients.first().name),
        name = this.drinkName,
        source = "openDB",
        isFavorite = this.isFavourite,
        remoteId = this.id
    )
}

fun Drink.Ingredient.toIngredient() = Ingredient(this.name, "", this.count)