package io.letDrink.localbar.db

import android.content.Context
import android.content.res.AssetManager
import androidx.room.Room
import com.google.gson.Gson
import io.letDrink.localbar.db.entity.Cocktail
import kotlinx.coroutines.runBlocking

class PrepopulateDBExecutor(
    context: Context,
    private val dbName: String? = null
) {

    companion object {
        const val DB_NAME = "db"
        const val DB_ASSET_PATH = "db/$DB_NAME"
    }

    internal val localBarDB: LocalBarDB by lazy {
        Room
            .databaseBuilder(context, LocalBarDB::class.java, dbName ?: DB_NAME)
            .createFromAsset(DB_ASSET_PATH)
            .fallbackToDestructiveMigration()
            .build()
    }
}