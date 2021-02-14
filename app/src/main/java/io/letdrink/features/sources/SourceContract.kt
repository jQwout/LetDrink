package io.letdrink.features.sources

import com.example.thecocktaildb.network.models.Drink

interface SourceContract {
    suspend fun random(): Drink
    suspend fun findByIngredient(ingredient: String): List<Drink>
    suspend fun getByIdDrink(id: Long) : Drink?
}