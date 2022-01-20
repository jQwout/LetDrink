package io.letdrink.features.favorites

import dagger.hilt.android.lifecycle.HiltViewModel
import io.letDrink.localbar.db.repository.CocktailFacade
import io.letdrink.common.viewmodel.BaseViewModel2
import io.letdrink.features.drink_list.DrinkItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val cocktailFacade: CocktailFacade
) : BaseViewModel2() {

    val uiState: MutableStateFlow<FavoritesUIState> = MutableStateFlow(FavoritesUIState.Empty)

    fun loadFavorites() {
        backgroundScope.launch {
            uiState.emit(FavoritesUIState.Loading)
            cocktailFacade.getFavourites().collect {
                try {
                    if (it.isEmpty()) {
                        uiState.emit(FavoritesUIState.Empty)
                    } else {
                        uiState.emit(
                            FavoritesUIState.Content(it.map(::DrinkItem))
                        )
                    }
                } catch (e: Throwable) {
                    uiState.emit(FavoritesUIState.Empty)
                }
            }
        }
    }
}

sealed class FavoritesUIState {
    object Empty : FavoritesUIState()
    object Loading : FavoritesUIState()
    class Content(val drinks: List<DrinkItem>) : FavoritesUIState()
}