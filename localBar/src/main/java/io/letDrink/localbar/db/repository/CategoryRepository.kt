package io.letDrink.localbar.db.repository

import kotlinx.coroutines.flow.map

open class CategoryRepository(private val cocktailRepository: CocktailRepository) {

    val keywords = cocktailRepository.getCocktails().map { list ->
        list.flatMap { it.keywords }.distinct()
    }
}