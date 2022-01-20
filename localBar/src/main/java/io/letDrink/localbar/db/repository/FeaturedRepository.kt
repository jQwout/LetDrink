package io.letDrink.localbar.db.repository

import io.letDrink.localbar.db.github.OpenDrinkGithubApi
import io.letDrink.localbar.db.pojo.CocktailDto
import io.letDrink.localbar.db.pojo.CocktailRaw
import io.letDrink.localbar.db.pojo.FeaturedItem
import io.letDrink.localbar.db.storage.CocktailsLocalStorage
import io.letDrink.localbar.db.storage.CocktailsRemoteStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class FeaturedRepository(private val api: OpenDrinkGithubApi) {

    suspend fun getFeatured(): List<FeaturedItem> {
        return api.getFeatured()
    }
}

class FeaturedFacade internal constructor(
    private val featuredRepository: FeaturedRepository,
    private val localStorage: CocktailsLocalStorage,
    private val remoteStorage: CocktailsRemoteStorage,
    private val favouritesRepository: FavouritesRepository
) {

    suspend fun getFeatured(): List<FeaturedItem> {
        return featuredRepository.getFeatured()
    }

    suspend fun getByNames(item: FeaturedItem): Flow<List<CocktailDto>> {
        val raws = item.items.map { getByName(it) }

        return favouritesRepository.get().map { favs ->
            raws.map {
                CocktailDto(it, favs.contains(it.name))
            }
        }
    }

    private suspend fun getByName(filename: String): CocktailRaw {
        return localStorage.get(filename) ?: remoteStorage.getCocktail(filename)
    }
}