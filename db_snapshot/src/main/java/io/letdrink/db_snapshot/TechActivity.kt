package io.letdrink.db_snapshot

import android.content.res.AssetManager
import android.os.Bundle
import android.widget.TextView
import androidx.core.content.edit
import androidx.fragment.app.FragmentActivity
import com.example.thecocktaildb.network.CocktailApi
import com.example.thecocktaildb.network.CocktailApiServiceLocator
import com.example.thecocktaildb.network.di.GsonFactory
import com.example.thecocktaildb.network.di.OkHttpFactory
import com.example.thecocktaildb.network.di.RetrofitProvider
import com.google.gson.Gson
import io.letDrink.localbar.db.CocktailConsumer
import io.letDrink.localbar.db.LocalBarServiceLocator
import io.letdrink.db_snapshot.asset.CocktailsJson
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread

class TechActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tech)
        val textView = findViewById<TextView>(R.id.techStatus)
        dbWork(textView)
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
        val locator = LocalBarServiceLocator(this, "db_with_images")
        val api = CocktailApiServiceLocator().api
        val gson = Gson()
        PopulateHelper(
            api,
            locator.cocktailConsumer,
            assets,
            gson
        )()

        runOnUiThread {
            textView.text = "выгрузка базы данных завершена"
        }
    }
}
