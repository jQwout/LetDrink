package io.letDrink.localbar

import io.letDrink.localbar.db.pojo.CocktailRaw
import kotlinx.coroutines.runBlocking
import org.junit.Test

class SimiliarAlgoTest {

    @Test
    fun test() = runBlocking {

        val drinks = TestDeps.recDir
            .listFiles()
            ?.map {
                TestDeps.gson.fromJson(it.readText(), CocktailRaw::class.java)
            } ?: return@runBlocking


        val random = drinks.random()

        val similiar = drinks
            .filter {
                it.keywords.intersect(random.keywords.toSet())
                    .isNotEmpty() and (it.name != random.name)
            }
            .sortedByDescending {
                it.keywords.intersect(random.keywords.toSet()).size
            }
            .take(5)

        println(drinks.indexOfFirst { it.name.contains("brain damage", true) })

        println(random.name)

        similiar.forEach {
            println(it.name)
        }
    }
}