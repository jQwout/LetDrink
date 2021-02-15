package io.letdrink.features.random

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thecocktaildb.network.models.Drink
import io.letdrink.common.state.SectionState
import io.letdrink.section.drink.DrinkHistory
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class RandomCocktailViewModel @ViewModelInject constructor(
    private val randomCocktailService: RandomCocktailService,
    private val drinkHistory: DrinkHistory
) : ViewModel() {

    val uiState: MutableStateFlow<RandomDrinkState> = MutableStateFlow(RandomDrinkState())

    private val lastState get() = uiState.value

    fun onStart() {
        viewModelScope.launch {
            drinkHistory.save(randomCocktailService.get())
            uiState.emit(
                RandomDrinkState(
                    drinkCardState = SectionState(
                        content = drinkHistory.list.first,
                        isLoading = false
                    ),
                    navBarSection = SectionState(
                        content = NavBarUi(
                            hasPrev = false,
                            hasNext = true
                        ),
                        isLoading = false
                    )
                )
            )
        }
    }

    fun onClickSimiliar(drink: Drink) {
        viewModelScope.launch {
            uiState.emit(
                RandomDrinkState(
                    drinkCardState = SectionState(
                        content = drink,
                        isLoading = false
                    ),
                    navBarSection = SectionState(
                        content = NavBarUi(
                            hasPrev = true,
                            hasNext = true
                        ),
                        isLoading = false
                    )
                )
            )
        }
    }

    fun onClickPrevDrink() = viewModelScope.launch {
        val prevDrink = drinkHistory.getPrev(lastState.drinkCardState.content)
        uiState.emit(
            RandomDrinkState(
                SectionState(prevDrink, false),
                SectionState(
                    NavBarUi(
                        hasPrev = drinkHistory.hasPrev(prevDrink),
                        hasNext = true
                    )
                )
            )
        )
    }

    fun onClickNextDrink() {
        viewModelScope.launch {
            val currentContent = lastState.drinkCardState.content
            val nextDrink = drinkHistory.getNext(currentContent)
            if (nextDrink == null) {
                uiState.emit(
                    RandomDrinkState(
                        drinkCardState = SectionState(isLoading = true),
                        navBarSection = SectionState(
                            NavBarUi(
                                hasPrev = true,
                                hasNext = false
                            )
                        )
                    )
                )
                drinkHistory.save(randomCocktailService.get())
                uiState.emit(
                    RandomDrinkState(
                        SectionState(drinkHistory.getLast(), false),
                        SectionState(
                            NavBarUi(
                                hasPrev = true,
                                hasNext = true
                            )
                        )
                    )
                )

            } else {
                uiState.emit(
                    RandomDrinkState(
                        SectionState(nextDrink, false),
                        SectionState(
                            NavBarUi(
                                hasPrev = true,
                                hasNext = true
                            )
                        )
                    )
                )
            }
        }
    }
}

data class RandomDrinkState(
    val drinkCardState: SectionState<Drink> = SectionState(null, true),
    val navBarSection: SectionState<NavBarUi> = SectionState()
)

data class NavBarUi(
    val hasPrev: Boolean = false,
    val hasNext: Boolean = false
)



