package io.letDrink.localbar.db.repository

import android.content.SharedPreferences
import io.letDrink.localbar.db.pojo.CocktailRaw
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map


class CocktailRepository {

    private val _cocktails: MutableList<CocktailRaw> = mutableListOf()

    internal fun addAll(list: List<CocktailRaw>) {
        _cocktails.clear()
        _cocktails.addAll(list)
    }

    fun getCocktails() = flowOf(_cocktails)
}


class FavouritesRepository(
    private val cocktailRepository: CocktailRepository,
    private val sharedPreferences: SharedPreferences
) {

    fun get() = cocktailRepository.getCocktails().map { list ->
        list.filter { raw ->
            getSet()?.contains(raw.name) ?: false
        }
    }

    fun add(cocktailRaw: CocktailRaw) {
        val set = getSet()?.toMutableSet() ?: mutableSetOf()
        set.add(cocktailRaw.name)
        sharedPreferences.edit().putStringSet(this::cocktailRepository.name, set).commit()
    }

    fun remove(cocktailRaw: CocktailRaw) {
        val set = getSet()?.toMutableSet() ?: return
        set.remove(cocktailRaw.name)
        sharedPreferences.edit().putStringSet(this::cocktailRepository.name, set).commit()
    }

    private fun getSet() = sharedPreferences.getStringSet(this::cocktailRepository.name, null)
}