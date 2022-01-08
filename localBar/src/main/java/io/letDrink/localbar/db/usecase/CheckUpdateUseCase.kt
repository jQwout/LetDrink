package io.letDrink.localbar.db.usecase

import android.content.SharedPreferences
import io.letDrink.localbar.db.storage.CocktailsLocalStorage
import io.letDrink.localbar.db.storage.CocktailsRemoteStorage

class CheckUpdateUseCase(
    val localStorage: CocktailsLocalStorage,
    val remoteStorage: CocktailsRemoteStorage,
    val sharedPreferences: SharedPreferences
) {

    suspend fun update() {
        if (checkNeedUpdate().not()) return

        val remoteList = remoteStorage.getCocktailsPreview().toMutableList()
        val localList = localStorage.getFileNames()

        remoteList.removeAll {
            localList.contains(it.name)
        }

        remoteList.map { remoteStorage.getCocktailRaw(it) }.forEach(localStorage::add)

    }

    private fun checkNeedUpdate(): Boolean {

        val curr = System.currentTimeMillis()
        val lastUpd = sharedPreferences.getLong(LAST_UPD_TIME, curr)

        return (curr - lastUpd) > DAY_IN_MS
    }

    companion object {
        const val LAST_UPD_TIME = "LAST_UPD_TIME"
        const val DAY_IN_MS = 86400000
    }
}