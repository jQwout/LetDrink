package io.letdrink.common.network.di

import com.google.gson.GsonBuilder
import com.example.thecocktaildb.network.models.Drink
import com.example.thecocktaildb.network.models.PreviewDrink
import com.example.thecocktaildb.network.parsers.DrinkParser
import io.letdrink.common.network.parsers.PreviewDrinkParser

class GsonFactory {
    val gson by lazy {
        GsonBuilder().apply {
            registerTypeAdapter(Drink::class.java, DrinkParser())
            registerTypeAdapter(PreviewDrink::class.java, PreviewDrinkParser())
        }.create()
    }
}