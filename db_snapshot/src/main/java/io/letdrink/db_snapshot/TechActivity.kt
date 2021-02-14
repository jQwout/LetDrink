package io.letdrink.db_snapshot

import android.content.Context
import android.content.res.AssetManager
import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceManager
import android.widget.TextView
import androidx.core.content.FileProvider
import androidx.core.content.edit
import androidx.fragment.app.FragmentActivity
import com.google.gson.Gson
import io.letDrink.localbar.db.CocktailConsumer
import io.letDrink.localbar.db.LocalBarServiceLocator
import io.letdrink.db_snapshot.asset.CocktailsJson
import kotlinx.coroutines.runBlocking
import java.io.File
import kotlin.concurrent.thread

class TechActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tech)
        val textView = findViewById<TextView>(R.id.techStatus)
        sharedPresTest(textView)

    }

    private fun sharedPresTest(textView: TextView) {
        thread {
            with(getSharedPreferences("prefs", MODE_PRIVATE)) {
                val isInit = this.getBoolean("init", false)
                runOnUiThread {
                    textView.text = "is init$isInit\n"
                }
                edit {
                    putBoolean("init", true)
                }
                val isInit2 = this.getBoolean("init", false)
                Thread.sleep(3000)
                runOnUiThread {
                    textView.setText("before $isInit after $isInit2\n")
                }
            }
        }
    }

    private fun dbWork(textView: TextView) = thread {
        val gson = Gson()
        val locator = LocalBarServiceLocator(this)
        populateFromAsset(assets, gson, locator.cocktailConsumer)
        runOnUiThread {
            textView.text = "выгрузка базы данных завершена"
        }
    }
}


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