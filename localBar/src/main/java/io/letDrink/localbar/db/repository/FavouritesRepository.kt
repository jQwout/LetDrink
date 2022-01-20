package io.letDrink.localbar.db.repository

import android.content.SharedPreferences
import io.letDrink.localbar.db.pojo.CocktailRaw
import kotlinx.coroutines.flow.*

class FavouritesRepository(
    private val sharedPreferences: SharedPreferences
) {

    private val flow = MutableSharedFlow<Set<String>>(1)

    init {
        flow.tryEmit(getSet())
    }

    fun get() = flow

    fun add(cocktailRaw: CocktailRaw) {
        val set = getSet().toMutableSet()
        set.add(cocktailRaw.name)
        putSet(set)
    }

    fun remove(cocktailRaw: CocktailRaw) {
        val set = getSet().toMutableSet()
        set.remove(cocktailRaw.name)
        putSet(set)
    }

    suspend fun change(cocktailRaw: CocktailRaw) {
        val set = getSet().toMutableSet()
        if (set.contains(cocktailRaw.name)) {
            set.remove(cocktailRaw.name)
        } else {
            set.add(cocktailRaw.name)
        }

        putSet(set)
        flow.emit(set)
    }

    private fun getSet(): Set<String> =
        sharedPreferences.getStringSet(this::class.simpleName, emptySet()) ?: emptySet()

    private fun putSet(set: Set<String>?) =
        sharedPreferences.edit().putStringSet(this::class.simpleName, set).commit()
}