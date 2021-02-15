package io.letdrink.common.storage

import android.content.Context
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.InputStream
import java.lang.reflect.Type
import javax.inject.Inject

class FilesStorage @Inject constructor(
    @ApplicationContext val context: Context,
    private val gson: Gson
) {

    fun <T> getFromAsset(path: String, typeToken: Type): T {
        return context.assets.open(path).readDataFromJson(gson, typeToken)
    }

    fun <T> getFromCache(path: String, typeToken: Type): T? {
        val file = File(context.cacheDir, path)
        if (!file.exists()) {
            file.createNewFile()
            return null
        }
        return file.inputStream().readDataFromJson<T>(gson, typeToken)
    }

    fun <T> putToCache(path: String, value: T) {
        val file = File(context.cacheDir, path)
        if (!file.exists()) {
            file.createNewFile()
        }
        file.writeText(gson.toJson(value))
    }

    fun getFromCacheAge(path: String): Long {
        val file = File(context.cacheDir, path)
        if (!file.exists()) {
            file.createNewFile()
        }
        return file.lastModified()
    }
}


fun <T> InputStream.readDataFromJson(gson: Gson, typeToken: Type): T {
    return gson.newJsonReader(reader())
        .use { jsonReader -> gson.fromJson(jsonReader, typeToken) }
}