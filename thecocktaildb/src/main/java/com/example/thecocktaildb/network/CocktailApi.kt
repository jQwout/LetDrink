package io.letdrink.features.random

import com.example.thecocktaildb.network.models.Drink
import com.example.thecocktaildb.network.models.PreviewDrink
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailApi {

    @GET("random.php")
    suspend fun random(): DrinkPayload

    @GET("filter.php")
    suspend fun findByIngredient(@Query("i") ingredient: String): PreviewDrinkPayload

    @GET("lookup.php")
    suspend fun getById(@Query("i") id: Long): DrinkPayload
}

class DrinkPayload(val drinks: List<Drink>)

class PreviewDrinkPayload(val drinks: List<PreviewDrink>)
