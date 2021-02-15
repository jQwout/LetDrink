package io.letdrink.features.sources

import com.example.thecocktaildb.network.models.Drink
import io.letDrink.localbar.db.CocktailConsumer
import io.letdrink.common.const.Constants
import io.letdrink.features.config.Config
import io.letdrink.features.ingredient.config.IngredientsByCategory
import io.letdrink.features.mapper.toCocktail
import io.letdrink.features.mapper.toIngredient
import io.letdrink.features.sources.local_bar.LocalSourceContract
import io.letdrink.features.sources.the_cocktail_db.NetworkSourceContract
import java.util.*
import javax.inject.Inject


class SourceWrapper @Inject constructor(
    private val network: NetworkSourceContract,
    private val local: LocalSourceContract,
    private val consumer: CocktailConsumer,
    private val ingredientsByCategory: IngredientsByCategory,
    private val config: Config
) {

    suspend fun random(simple: Boolean): Drink {
        return if (simple) {
            val ing = ingredientsByCategory.simpleAlco.random()
            network.findByIngredient(ing).random()
        } else {
            network.random()
        }.apply {
            addToLocal(this)
        }
    }

    suspend fun getByIdDrink(id: Long): Drink {
        return local.getByIdDrink(id) ?: network.getByIdDrink(id)
    }

    private fun defineSource(): SourceContract {
        val random = Random()
        return if (random.nextInt(1) == 0 && !config.useOnlyLocal) {
            try {
                network
            } catch (e: Throwable) {
                local
            }
        } else {
            local
        }
    }

    private suspend fun addToLocal(drink: Drink) {
        consumer.add(drink.toCocktail(), drink.ingredients.map { i ->
            i.toIngredient()
        })
    }
}