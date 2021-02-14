package io.letdrink.features.sources.the_cocktail_db

import com.example.thecocktaildb.network.models.Drink
import io.letdrink.features.random.CocktailApi
import io.letdrink.features.sources.SourceContract
import javax.inject.Inject

class NetworkSourceContract @Inject constructor(private val api: CocktailApi) : SourceContract {

    override suspend fun random(): Drink {
        return api.random().drinks.first()
    }

    override suspend fun findByIngredient(ingredient: String): List<Drink> {
        return api.findByIngredient(ingredient).drinks
            .take(7)
            .map { api.getById(it.id).drinks }
            .flatten()
    }

    override suspend fun getByIdDrink(id: Long): Drink {
        return api.getById(id).drinks.first()
    }
}