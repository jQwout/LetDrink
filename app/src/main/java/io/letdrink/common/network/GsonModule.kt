package io.letdrink.common.network

import com.example.thecocktaildb.network.models.Drink
import com.example.thecocktaildb.network.models.PreviewDrink
import com.example.thecocktaildb.network.parsers.DrinkParser
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.letdrink.common.network.parsers.PreviewDrinkParser

@Module
@InstallIn(ApplicationComponent::class)
object GsonModule {

    @Provides
    fun provideGson(): Gson {
        return GsonBuilder().apply {
            registerTypeAdapter(Drink::class.java, DrinkParser())
            registerTypeAdapter(PreviewDrink::class.java, PreviewDrinkParser())
        }.create()
    }

}