package io.letDrink.localbar.db.utils

import io.letDrink.localbar.db.pojo.CocktailRaw

fun List<CocktailRaw>.filterByKeywords(cocktailRaw: CocktailRaw): List<CocktailRaw> {
    return filter {
        it.keywords.intersect(cocktailRaw.keywords.toSet())
            .isNotEmpty() and (it.name != cocktailRaw.name)
    }
        .sortedByDescending {
            it.keywords.intersect(cocktailRaw.keywords.toSet()).size
        }
        .take(5)
}

fun List<CocktailRaw>.filterByNameContains(chars: String): List<CocktailRaw> {
    return filter { it.name.contains(chars) }
}

fun List<CocktailRaw>.filterByIngredientName(chars: String): List<CocktailRaw> {
    return filter { it.ingredients.any { it.ingredient.contains(chars) } }
}

fun List<CocktailRaw>.filterByKeywordName(chars: String): List<CocktailRaw> {
    return filter { it.keywords.any { it.contains(chars) } }
}