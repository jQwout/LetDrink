package io.letdrink.section.drink

import com.example.thecocktaildb.network.models.Drink
import com.example.thecocktaildb.network.models.PreviewDrink
import io.letdrink.common.state.ScreenState
import io.letdrink.common.state.SectionState

data class DrinkCardState(
    val drink: SectionState<Drink>,
    val similiar: SectionState<List<Drink>>
) : ScreenState()