package io.letdrink

import io.letDrink.localbar.db.entity.Cocktail
import io.letdrink.common.network.di.GsonFactory
import com.example.thecocktaildb.network.models.Drink
import org.junit.Test

class ParseDrinkTest {

    @Test
    fun test() {
        val file = "/main_drink.json"
        val raw = checkNotNull(
            javaClass.getResourceAsStream(file)?.bufferedReader()?.readText()
        )
        val d = GsonFactory().gson.fromJson(raw, Drink::class.java)

        println(d)
    }

    @Test
    fun test2() {
        val file = "/error_drink.json"
        val raw = checkNotNull(
            javaClass.getResourceAsStream(file)?.bufferedReader()?.readText()
        )
        val d = GsonFactory().gson.fromJson(raw, Drink::class.java)

        println(d)
    }

    @Test
    fun test3() {
        val file = "/aam-panna.json"
        val raw = checkNotNull(
            javaClass.getResourceAsStream(file)?.bufferedReader()?.readText()
        )
        val d = GsonFactory().gson.fromJson(raw, Cocktail::class.java)

        println(d)
    }

}