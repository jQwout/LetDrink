package com.example.thecocktaildb.network.parsers

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.example.thecocktaildb.network.models.Drink
import java.lang.reflect.Type

class DrinkParser : JsonDeserializer<Drink> {

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): Drink {
        with(json as JsonObject) {
            return Drink(
                id = getId("idDrink"),
                drinkName = getStr("strDrink"),
                tags = getStrOrNULL("strTags"),
                ingredients = getIngredients(),
                alcoholic = getStr("strAlcoholic"),
                instructions = getStr("strInstructions"),
                imageSource = getStrOrNULL("strImageSource"),
                categories = getStrOrNULL("strCategory"),
                drinkThumb = getStr("strDrinkThumb"),
                description = null,
                isFavourite = false
            )
        }
    }

    private fun JsonObject.getIngredients(): List<Drink.Ingredient> {
        val i = mutableListOf<String>()
        val m = mutableListOf<String>()
        this.entrySet().forEach {
            if (!it.value.isJsonNull) {
                when {
                    it.key.contains("Ingredient") -> {
                        i.add(it.value.asString)
                    }
                    it.key.contains("Measure") -> {
                        m.add(it.value.asString)
                    }
                }
            }
        }
        return i.mapIndexed { index, ing -> ing to m.getOrElse(index) { " " } }.map {
            Drink.Ingredient(it.first, it.second)
        }
    }
}


fun JsonObject.getStr(key: String) = get(key).asString
fun JsonObject.getStrOrNULL(key: String) = get(key).let {
    if (it.isJsonNull)
        null
    else
        it.asString
}

fun JsonObject.getId(key: String) = get(key).asLong

