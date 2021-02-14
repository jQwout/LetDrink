package io.letdrink.features.favorites

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import io.letDrink.localbar.db.CocktailRepository
import io.letDrink.localbar.db.FavouritesRepository
import io.letDrink.localbar.db.LocalBarServiceLocator
import io.letdrink.features.drink_list.DrinkItem
import io.letdrink.features.mapper.toDrink
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FavoritesViewModel @ViewModelInject constructor(
    private val favoritesRepository: FavouritesRepository
) : ViewModel() {

    val uiState: MutableStateFlow<FavoritesUIState> = MutableStateFlow(FavoritesUIState.Empty)

    fun loadFavorites() {
        viewModelScope.launch {
            uiState.emit(FavoritesUIState.Loading)
            delay(1000)

            favoritesRepository.observeFavourites().collect {
                try {
                    if (it.isEmpty()) {
                        uiState.emit(FavoritesUIState.Empty)
                    } else {
                        val items = it.map { c -> DrinkItem(c.toDrink()) }
                        uiState.emit(FavoritesUIState.Content(items))
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