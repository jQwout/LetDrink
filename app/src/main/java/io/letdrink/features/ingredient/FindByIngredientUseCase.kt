package io.letdrink.features.ingredient

import com.example.thecocktaildb.network.models.Drink
import io.letDrink.localbar.db.CocktailConsumer
import io.letdrink.common.const.Constants
import io.letdrink.features.mapper.toCocktail
import io.letdrink.features.mapper.toIngredient
import io.letdrink.features.sources.the_cocktail_db.NetworkSourceContract
import javax.inject.Inject

class FindByIngredientUseCase @Inject constructor(
    private val network: NetworkSourceContract,
    private val local: LocalSourceContract,
    private val consumer: CocktailConsumer
) {

    suspend fun find(drink: Drink): List<Drink> {
        val list = ArrayList<Drink>()
        val candidate = ArrayList<Drink>()
        for (it in drink.ingredients) {

            val localList = local.findByIngredient(it.name).filter { it != drink }

            if (localList.isEmpty() || localList.size == 1) {

                network.findByIngredient(it.name)
                    .take(Constants.SIMILIAR.SIZE_NETWORK)
                    .filter { it != drink }
                    .map { network.getByIdDrink(it.id) }
                    .forEach { list.add(it);addToLocal(it) }

            } else {
                list.addAll(localList.take(Constants.SIMILIAR.SIZE_LOCAL))
            }

            if (localList.size == 1) {
                candidate.addAll(localList)
            } else if (localList.isNotEmpty()) {
                break
            }
        }
        return list
    }

    private suspend fun addToLocal(drink: Drink) {
        consumer.add(
            drink.toCocktail(), drink.ingredients.map { i -> i.toIngredient() }
        )
    }
}