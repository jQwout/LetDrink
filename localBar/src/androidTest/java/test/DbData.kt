import android.content.res.AssetManager
import com.google.gson.Gson
import io.letDrink.localbar.db.CocktailConsumer
import io.letDrink.localbar.db.entity.Cocktail
import io.letDrink.localbar.db.entity.Ingredient
import kotlinx.coroutines.runBlocking

object DbDataProxy {

    fun populateFromAsset(
        assetManager: AssetManager,
        gson: Gson,
        cocktailConsumer: CocktailConsumer
    ) {
        val jsons = assetManager.list("recipes")

        val cocktails = jsons?.mapNotNull {
            try {
                gson.fromJson(
                    assetManager.open("recipes/$it").reader().readText(),
                    CocktailsJson::class.java
                )
            } catch (e: Throwable) {
                println("error : $it")
                null
            }
        }

        cocktails?.let { list ->
            runBlocking {
                list.forEach {
                    cocktailConsumer.add(it.getCocktails(), it.ingredients)
                }
            }
        }
    }

    internal class CocktailsJson(
        val description: String,
        val directions: List<String>,
        val github: String,
        val image: String,
        val keywords: List<String>,
        val name: String,
        val source: String?,
        val ingredients: List<Ingredient>
    ) {
        fun getCocktails() = Cocktail(
            description, directions, github, image, keywords, name, source
        )
    }
}