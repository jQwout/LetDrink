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
) : BaseViewModel<DrinkCardState>() {

    override val uiState: MutableStateFlow<DrinkCardState> = MutableStateFlow(
        DrinkCardState()
    )

    fun changeFavorite(drink: Drink) = backgroundScope.launch {
        val newDrink = drink.copy(isFavourite = !drink.isFavourite)
        if (newDrink.isFavourite) {
            favoritesRepository.add(newDrink.toCocktail())
        } else {
            favoritesRepository.remove(newDrink.toCocktail())
        }
        delay(50)
        uiState.emit(lastState.copy(favourite = SectionState(newDrink)))
    }

    fun loadSimiliar(drink: Drink) = backgroundScope.launch {
        val similiar = findByIngredientUseCase.find(drink)
        uiState.emit(
            lastState.copy(
                similiar = SectionState(similiar),
                favourite = SectionState(drink)
            )
        )
    }
}

class DrinkCardState(
    val similiar: SectionState<List<Drink>> = SectionState(),
    val favourite: SectionState<Drink> = SectionState()
) {
    fun copy(
        similiar: SectionState<List<Drink>> = SectionState(),
        favourite: SectionState<Drink> = SectionState()
    ): DrinkCardState {
        return DrinkCardState(similiar, favourite)
    }
}