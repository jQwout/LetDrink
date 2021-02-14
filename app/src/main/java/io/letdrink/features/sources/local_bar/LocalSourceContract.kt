package io.letdrink.features.sources.local_bar

import io.letDrink.localbar.db.CocktailRepository
import com.example.thecocktaildb.network.models.Drink
import io.letDrink.localbar.db.entity.CocktailWithIngredients
import io.letdrink.features.mapper.toDrink
import io.letdrink.features.sources.SourceContract

class LocalSourceContract(private val repository: CocktailRepository) : SourceContract {

    override suspend fun random(): Drink {
        return repository.getRandom().toDrink()
    }

    override suspend fun findByIngredient(ingredient: String): List<Drink> {
        return repository.getByIngredients(ingredient).map { it.toDrink() }
    }

    override suspend fun getByIdDrink(id: Long): Drink? {
        return repository.getById(id)?.toDrink()
    }
}