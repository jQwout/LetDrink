package io.letdrink.common.network.parsers

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.example.thecocktaildb.network.models.PreviewDrink
import com.example.thecocktaildb.network.parsers.getId
import com.example.thecocktaildb.network.parsers.getStr
import java.lang.reflect.Type

class PreviewDrinkParser : JsonDeserializer<PreviewDrink> {

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): PreviewDrink {
        with(json as JsonObject) {
            return PreviewDrink(
                id = getId("idDrink"),
                drinkName = getStr("strDrink"),
                drinkThumb = getStr("strDrinkThumb")
            )
        }
    }
}
