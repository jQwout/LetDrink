package io.letdrink.section.drink

import com.example.thecocktaildb.network.models.Drink
import com.mikepenz.fastadapter.dsl.genericFastAdapter
import java.util.*
import javax.inject.Inject

class DrinkHistory @Inject constructor() {

    val list = LinkedList<Drink>()

    fun getPrev(drink: Drink?): Drink? {
        drink ?: return null
        val i = getIndex(drink) ?: return null
        return list.getOrNull(i - 1)
    }

    fun getNext(drink: Drink?): Drink? {
        drink ?: return null
        val i = getIndex(drink) ?: return null
        return list.getOrNull(i + 1)
    }

    fun hasNext(drink: Drink?): Boolean {
        drink ?: return false
        val i = getIndex(drink) ?: return false
        return list.getOrNull(i + 1) != null
    }

    fun hasPrev(drink: Drink?): Boolean {
        drink ?: return false
        val i = getIndex(drink) ?: return false
        return list.getOrNull(i - 1) != null
    }

    fun saveSimiliar(current: Drink, drink: Drink) {
        val i = getIndex(current)
        if (i == null)
            list.add(drink)
        else
            list.set(i , drink)
    }

    fun save(drinks: List<Drink>) {
        drinks.forEach {
            list.add(it)
        }
    }

    fun save(drink: Drink) {
        list.add(drink)
    }

    fun getLast() = list.last

    private fun getIndex(drink: Drink): Int? {
        val i = list.lastIndexOf(drink)
        return if (i == -1) null else i
    }
}