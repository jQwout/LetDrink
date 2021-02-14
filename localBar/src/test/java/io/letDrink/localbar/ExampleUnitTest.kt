package io.letDrink.localbar

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.letDrink.localbar.db.LocalBarDB
import io.letDrink.localbar.db.converter.type
import io.letDrink.localbar.db.entity.Ingredient
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val price = 11_000_000
        val morategePayment = 99_824
        val eachMounth  = 60_000
        val yearLong = 15
        val mortagePercent = 7
        val depositePercent = 5


        val mortageSum =  yearLong * morategePayment

        println(mortageSum)
    }
}

private fun scorePercents(sum: Int, double: Double) = (sum / 100) * double
