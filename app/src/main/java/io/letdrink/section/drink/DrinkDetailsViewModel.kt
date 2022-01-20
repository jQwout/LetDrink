package io.letdrink.section.drink

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.letDrink.localbar.db.pojo.CocktailDto
import io.letdrink.common.viewmodel.BaseViewModel
import io.letdrink.features.random.NavBarUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrinkDetailsViewModel @Inject constructor(
    private val drinkHistory: DrinkHistory
) : BaseViewModel<DrinkDetailsState>() {

    override val uiState: MutableStateFlow<DrinkDetailsState> =
        MutableStateFlow(DrinkDetailsState())

    fun load(drink: CocktailDto) {
        viewModelScope.launch {
            drinkHistory.save(drink)
            uiState.emit(
                DrinkDetailsState(drink)
            )
        }
    }

    fun onClickNextDrink() {
        viewModelScope.launch {
            val next = drinkHistory.getNext(lastState.drink)
            uiState.emit(
                lastState.copy(
                    drink = next,
                    navBarUi = lastState.navBarUi.copy(
                        hasPrev = true,
                        hasNext = drinkHistory.hasNext(next)
                    )
                )
            )
        }
    }

    fun onClickPrevDrink() {
        viewModelScope.launch {
            val prev = drinkHistory.getPrev(lastState.drink)
            uiState.emit(
                lastState.copy(
                    drink = prev,
                    navBarUi = lastState.navBarUi.copy(
                        hasPrev = drinkHistory.hasPrev(prev),
                        hasNext = true
                    )
                )
            )
        }
    }

    fun onSimiliarClick(drink: CocktailDto) {
        drinkHistory.save(drink)
        viewModelScope.launch {
            uiState.emit(
                lastState.copy(
                    drink = drink,
                    navBarUi = lastState.navBarUi.copy(hasPrev = true)
                )
            )
        }
    }

}

data class DrinkDetailsState(
    val drink: CocktailDto? = null,
    val navBarUi: NavBarUi = NavBarUi()
)