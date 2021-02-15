package io.letdrink.db_snapshot.asset

import io.letDrink.localbar.db.entity.Cocktail
import io.letDrink.localbar.db.entity.Ingredient

internal data class CocktailsJson(
    val description: String,
    val directions: List<String>,
    val github: String,
    val image: String,
    val keywords: List<String>,
    val name: String,
    val source: String?,
    val ingredients: List<Ingredient>,
    val localName: String? = null
) {

    fun getCocktails() = Cocktail(
        description, directions, github, image, keywords, name, source, localFileName = localName
    )
}