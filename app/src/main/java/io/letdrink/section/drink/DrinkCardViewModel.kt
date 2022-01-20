package io.letdrink.section.drink

import dagger.hilt.android.lifecycle.HiltViewModel
import io.letDrink.localbar.db.pojo.CocktailDto
import io.letDrink.localbar.db.pojo.CocktailRaw
import io.letDrink.localbar.db.repository.CocktailFacade
import io.letDrink.localbar.db.repository.CocktailRepository
import io.letDrink.localbar.db.repository.FavouritesRepository
import io.letdrink.common.state.SectionState
import io.letdrink.common.viewmodel.BaseViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class DrinkCardViewModel @Inject constructor(
    private val cocktailFacade: CocktailFacade
) : BaseViewModel<DrinkCardState>() {

    override val uiState: MutableStateFlow<DrinkCardState> = MutableStateFlow(
        DrinkCardState()
    )

    fun changeFavorite(dto: CocktailDto) = backgroundScope.launch {
        cocktailFacade.changeFavourite(dto)
        uiState.emit(
            lastState.copy(
                favourite = SectionState(
                    CocktailDto(
                        dto.data,
                        dto.isFavourite.not()
                    )
                )
            )
        )
    }

    fun loadSimiliar(cocktailRaw: CocktailDto) = backgroundScope.launch {
        cocktailFacade.getLikeA(cocktailRaw).collect {
            uiState.emit(
                lastState.copy(
                    similiar = SectionState(it)
                )
            )
        }
    }
}

class DrinkCardState(
    val similiar: SectionState<List<CocktailDto>> = SectionState(),
    val favourite: SectionState<CocktailDto> = SectionState()
) {
    fun copy(
        similiar: SectionState<List<CocktailDto>> = SectionState(),
        favourite: SectionState<CocktailDto> = SectionState()
    ): DrinkCardState {
        return DrinkCardState(similiar, favourite)
    }
}