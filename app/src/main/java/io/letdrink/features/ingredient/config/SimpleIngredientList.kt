package io.letdrink.features.ingredient.config

import javax.inject.Inject

class IngredientsByCategory @Inject constructor() {
    val simpleAlco = listOf(
        "gin", "whiskey", "vodka", "wine", "run", "beer", "tequila", "triple sec"
    )
}