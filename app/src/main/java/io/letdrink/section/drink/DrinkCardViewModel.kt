package io.letdrink.section.drink

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.letDrink.localbar.db.CocktailRepository
import com.example.thecocktaildb.network.models.Drink
import io.letDrink.localbar.db.FavouritesRepository
import io.letdrink.common.state.SectionState
import io.letdrink.common.utils.cancelChildrenAndCreateNew
import io.letdrink.common.viewmodel.BaseViewModel
import io.letdrink.features.ingredient.FindByIngredientUseCase
import io.letdrink.features.mapper.toCocktail
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class DrinkCardViewModel @ViewModelInject constructor(
    private val favoritesRepository: FavouritesRepository,
    private val findByIngredientUseCase: FindByIngredientUseCase
) : BaseViewModel<DrinkCardState2>() {

    override val uiState: MutableStateFlow<DrinkCardState2> = MutableStateFlow(DrinkCardState2())

    private var changeFavoriteJob = SupervisorJob()
    private var loadSimiliarJob = SupervisorJob()

    fun changeFavorite(drink: Drink) {
        changeFavoriteJob = changeFavoriteJob.cancelChildrenAndCreateNew()
        asyncOnce(
            job = changeFavoriteJob,
            onIO = {
                val newDrink = drink.copy(isFavourite = !drink.isFavourite)
                if (newDrink.isFavourite) {
                    favoritesRepository.add(newDrink.toCocktail())
                } else {
                    favoritesRepository.remove(newDrink.toCocktail())
                }
                newDrink
            },
            onUI = { uiState.emit(lastState.copy(favourite = SectionState(it))) }
        )
    }

    fun loadSimiliar(drink: Drink) {
        loadSimiliarJob = loadSimiliarJob.cancelChildrenAndCreateNew()
        asyncOnce(
            job = loadSimiliarJob,
            onIO = { findByIngredientUseCase.find(drink) },
            onUI = { uiState.emit(lastState.copy(similiar = SectionState(it))) }
        )
    }

}

data class DrinkCardState2(
    val similiar: SectionState<List<Drink>> = SectionState(),
    val favourite: SectionState<Drink> = SectionState()
)