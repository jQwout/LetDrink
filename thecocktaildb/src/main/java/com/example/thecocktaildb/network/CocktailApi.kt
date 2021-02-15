package com.example.thecocktaildb.network

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

    @GET("search.php")
    suspend fun getByName(@Query("s") s: String): DrinkPayloadNullable
}

class DrinkPayload(val drinks: List<Drink>)

class PreviewDrinkPayload(val drinks: List<PreviewDrink>)

class DrinkPayloadNullable(val drinks: List<Drink>?)
