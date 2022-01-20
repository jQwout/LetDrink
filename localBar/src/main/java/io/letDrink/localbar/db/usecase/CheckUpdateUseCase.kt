package io.letDrink.localbar.db.usecase

import android.content.SharedPreferences
import io.letDrink.localbar.db.storage.CocktailsLocalStorage
import io.letDrink.localbar.db.storage.CocktailsRemoteStorage

class CheckUpdateUseCase(
    val localStorage: CocktailsLocalStorage,
    val remoteStorage: CocktailsRemoteStorage,
    val sharedPreferences: SharedPreferences,
    val populateRepositoryUseCase: PopulateRepositoryUseCase
) {

    suspend fun update() {
        localStorage.prepareFiles()

        if (checkNeedUpdate()) {

            val remoteList = remoteStorage.getCocktailsPreview().toMutableList()
            val localList = localStorage.getFileNames()

            remoteList.removeAll {
                localList.contains(it.name)
            }

            remoteList.forEach {
                val raw = remoteStorage.getCocktailRaw(it)
                localStorage.add(raw, it.name)
            }
        }

        populateRepositoryUseCase.populate()
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