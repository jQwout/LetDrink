package io.letDrink.localbar.db.storage

import com.google.gson.Gson
import io.letDrink.localbar.db.github.OpenDrinkGithubApi
import io.letDrink.localbar.db.github.OpenDrinkGithubApi.Companion.STORAGE_RAW_URL
import io.letDrink.localbar.db.pojo.CocktailRaw
import io.letDrink.localbar.db.pojo.CocktailsPreviewRaw
import java.io.File

class CocktailsLocalStorage(
    val dir: File, val gson: Gson
) {

    fun add(cocktailRaw: CocktailRaw) {
        val file = File(dir, cocktailRaw.name)
        val str = gson.toJson(cocktailRaw)
        file.writeText(str)
    }

    fun get(): List<CocktailRaw> {
        return dir.listFiles()
            .map { gson.fromJson(it.readText(), CocktailRaw::class.java) }
    }

    internal fun getFileNames(): List<String> {
        return dir.list().toList()
    }
}

class CocktailsRemoteStorage(
    val api: OpenDrinkGithubApi
) {

    internal suspend fun getCocktailsPreview(): List<CocktailsPreviewRaw> {
        return api.getCocktails(STORAGE_RAW_URL)
    }

    suspend fun getCocktail(name: String): CocktailRaw {
        return api.getCocktail(name)
    }

    suspend fun getCocktailRaw(preview: CocktailsPreviewRaw): CocktailRaw {
        return api.getCocktailRaw(preview.link)
    }
}