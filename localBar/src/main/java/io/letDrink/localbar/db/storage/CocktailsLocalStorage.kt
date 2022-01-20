package io.letDrink.localbar.db.storage

import android.content.res.AssetManager
import com.google.gson.Gson
import io.letDrink.localbar.db.github.OpenDrinkGithubApi
import io.letDrink.localbar.db.github.OpenDrinkGithubApi.Companion.STORAGE_RAW_URL
import io.letDrink.localbar.db.pojo.CocktailRaw
import io.letDrink.localbar.db.pojo.CocktailsPreviewRaw
import java.io.File

class CocktailsLocalStorage(
    val dir: File,
    val gson: Gson,
    val assetManager: AssetManager
) {

    private fun getFiles(): List<File> {
        return dir.listFiles().toList()
    }

    fun prepareFiles() {
        if (dir.exists()) {

            if (dir.isDirectory.not()) {
                dir.delete()
                dir.mkdir()
            }

        } else {
            dir.mkdir()
        }

        if (dir.listFiles().isNullOrEmpty().not()) return

        assetManager.list("recipes")?.map {
            val txt = assetManager.open("recipes/$it").reader().readText()
            val raw = gson.fromJson(txt, CocktailRaw::class.java)
            add(raw, it)
        }
    }

    fun add(cocktailRaw: CocktailRaw, filename: String): File {
        val file = File(dir, filename)
        val str = gson.toJson(cocktailRaw)
        file.createNewFile()
        file.writeText(str)

        return file
    }

    fun get(): List<CocktailRaw> {
        return getFiles().map {
            gson.fromJson(it.readText(), CocktailRaw::class.java)
        }
    }

    fun get(filename: String): CocktailRaw? {
        return getFiles()
            .firstOrNull{ it.name.contains(filename, false) }
            ?.let {
                gson.fromJson(it.readText(), CocktailRaw::class.java)
            }
    }

    internal fun getFileNames(): List<String> {
        return getFiles().map { it.name }
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