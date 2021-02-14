package test

import DbDataProxy
import android.content.Context
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.gson.Gson
import io.letDrink.localbar.db.CocktailConsumer
import io.letDrink.localbar.db.CocktailRepository
import io.letDrink.localbar.db.FavouritesRepository
import io.letDrink.localbar.db.LocalBarDB
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.concurrent.thread

@RunWith(AndroidJUnit4::class)
class FavouriteRepositoryTest {

    private lateinit var context: Context

    private lateinit var db: LocalBarDB

    private val favouritesRepository by lazy {
        FavouritesRepository(db.favouritesDao)
    }

    private val cocktailRepository by lazy { CocktailRepository(db.cocktailDao, db.ingredientDao) }

    private val consumer by lazy {
        CocktailConsumer(db.ingredientDao, db.cocktailDao, db.cocktailWithIngredientDao)
    }

    @Before
    fun createDb() {
        context = InstrumentationRegistry.getInstrumentation().context
        db = Room.inMemoryDatabaseBuilder(context, LocalBarDB::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun flowTest() {
        DbDataProxy.populateFromAsset(
            assetManager = context.assets, Gson(), consumer
        )
        runBlocking {

            val cocktailWithIngredients = cocktailRepository.getByName("Apple Jack").first()

            thread {
                runBlocking {
                    favouritesRepository.observeFavourites().collect {
                        println(it.size)
                    }
                }
            }

            favouritesRepository.add(cocktailWithIngredients.cocktail)
            favouritesRepository.remove(cocktailWithIngredients.cocktail)
            favouritesRepository.add(cocktailWithIngredients.cocktail)
        }

        Thread.sleep(10_000)
    }
}