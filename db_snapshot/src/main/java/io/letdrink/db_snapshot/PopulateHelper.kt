package io.letdrink.db_snapshot

import android.content.res.AssetManager
import com.example.thecocktaildb.network.CocktailApi
import com.google.gson.Gson
import io.letDrink.localbar.db.CocktailConsumer
import io.letdrink.db_snapshot.asset.CocktailsJson
import kotlinx.coroutines.runBlocking

class PopulateHelper(
    private val cocktailApi: CocktailApi,
    private val consumer: CocktailConsumer,
    private val assetManager: AssetManager,
    private val gson: Gson,
) {

    operator fun invoke() {

        val cocktails = assetManager.list("recipes")
            ?.mapNotNull { getCocktailsJson(it) }
            ?.filter { it.keywords.contains("alcoholic") }
         // ?.mapNotNull { json ->
         //     val list = runBlocking { cocktailApi.getByName(json.name) }.drinks ?: return@mapNotNull null
         //     val first = list.takeIf { it.isNotEmpty() }?.first() ?:  return@mapNotNull null
         //     json.copy(image = first.getImage())
         // }

        runBlocking {
            cocktails?.let { list ->
                list.forEach {
                    //   consumer.add(it.getCocktails(), it.ingredients)
                }
            }
        }
    }

    private fun getCocktailsJson(it: String): CocktailsJson? {
        return try {
            gson
                .fromJson(
                    assetManager.open("recipes/$it").reader().readText(),
                    CocktailsJson::class.java
                )
                .copy(localName = it)
        } catch (e: Throwable) {
            println("error : $it")
            null
        }
    }
}