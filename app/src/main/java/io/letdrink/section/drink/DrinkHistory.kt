package io.letdrink.section.drink

import io.letDrink.localbar.db.pojo.CocktailDto
import java.util.*
import javax.inject.Inject

class DrinkHistory @Inject constructor() {

    val list = LinkedList<CocktailDto>()

    fun getPrev(drink: CocktailDto?): CocktailDto? {
        drink ?: return null
        val i = getIndex(drink) ?: return null
        return list.getOrNull(i - 1)
    }

    fun getNext(drink: CocktailDto?): CocktailDto? {
        drink ?: return null
        val i = getIndex(drink) ?: return null
        return list.getOrNull(i + 1)
    }

    fun hasNext(drink: CocktailDto?): Boolean {
        drink ?: return false
        val i = getIndex(drink) ?: return false
        return list.getOrNull(i + 1) != null
    }

    fun hasPrev(drink: CocktailDto?): Boolean {
        drink ?: return false
        val i = getIndex(drink) ?: return false
        return list.getOrNull(i - 1) != null
    }

    fun saveSimiliar(current: CocktailDto, drink: CocktailDto) {
        val i = getIndex(current)
        if (i == null)
            list.add(drink)
        else
            list.set(i , drink)
    }

    fun save(drinks: List<CocktailDto>) {
        drinks.forEach {
            list.add(it)
        }
    }

    fun save(drink: CocktailDto) {
        list.add(drink)
    }

    fun getLast() = list.last

    private fun getIndex(drink: CocktailDto): Int? {
        val i = list.lastIndexOf(drink)
        return if (i == -1) null else i
    }
}