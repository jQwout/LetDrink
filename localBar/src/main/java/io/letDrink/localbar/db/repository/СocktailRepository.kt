package io.letDrink.localbar.db.repository

import io.letDrink.localbar.db.pojo.CocktailDto
import io.letDrink.localbar.db.pojo.CocktailRaw
import io.letDrink.localbar.db.utils.filterByKeywords
import kotlinx.coroutines.flow.*


open class CocktailRepository {

    private val _cocktails: MutableList<CocktailRaw> = mutableListOf()

    internal fun addAll(list: List<CocktailRaw>) {
        _cocktails.clear()
        _cocktails.addAll(list)
    }

    fun getCocktails() = flowOf(_cocktails)
}


class CocktailFacade(
    val cocktailRepository: CocktailRepository,
    val favouritesRepository: FavouritesRepository,
) {

    fun get(): Flow<List<CocktailDto>> {
        return cocktailRepository.getCocktails()
            .combineToCocktailDto()
    }

    fun getFavourites(): Flow<List<CocktailDto>> {
        return cocktailRepository.getCocktails()
            .combineToCocktailDto()
            .map {
                it.filter { it.isFavourite }
            }
    }

    fun getLikeA(cocktailDto: CocktailDto): Flow<List<CocktailDto>> {
        return cocktailRepository.getCocktails()
            .map { it.filterByKeywords(cocktailDto.data) }
            .combineToCocktailDto()
    }

    suspend fun changeFavourite(cocktailDto: CocktailDto) {
        favouritesRepository.change(cocktailDto.data)
    }

    private fun Flow<List<CocktailRaw>>.combineToCocktailDto(): Flow<List<CocktailDto>> {
        return combine(favouritesRepository.get()) { c, f ->
            c.map {
                CocktailDto(it, f.contains(it.name))
            }
        }
    }
}
