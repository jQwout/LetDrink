package io.letdrink.features.random

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.letDrink.localbar.db.pojo.CocktailDto
import io.letdrink.common.state.SectionState
import io.letdrink.common.viewmodel.BaseViewModel2
import io.letdrink.section.drink.DrinkHistory
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class RandomCocktailViewModel @Inject constructor(
    private val randomCocktailService: RandomCocktailService,
    private val drinkHistory: DrinkHistory
) : BaseViewModel2() {

    val uiState: MutableStateFlow<RandomDrinkState> = MutableStateFlow(RandomDrinkState())

    private val lastState get() = uiState.value

    fun onStart() {
        backgroundScope.launch {
            randomCocktailService.get().collect {
                drinkHistory.save(it)
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
    }

    fun onClickSimiliar(drink: CocktailDto) {
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
        backgroundScope.launch {
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
                randomCocktailService.get().collect {
                    drinkHistory.save(it)
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

                }
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
    val drinkCardState: SectionState<CocktailDto> = SectionState(null, true),
    val navBarSection: SectionState<NavBarUi> = SectionState()
)

data class NavBarUi(
    val hasPrev: Boolean = false,
    val hasNext: Boolean = false
)



