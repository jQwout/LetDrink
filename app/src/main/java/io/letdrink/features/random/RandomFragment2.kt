package io.letdrink.features.random

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import io.letDrink.localbar.db.pojo.CocktailDto
import io.letdrink.R
import io.letdrink.common.state.SectionState
import io.letdrink.section.drink.DrinkCardFragment
import io.letdrink.section.drink.SelectSimiliarDrinkListener
import kotlinx.android.synthetic.main.fragment_random.backFab
import kotlinx.android.synthetic.main.fragment_random.forwardFab
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class RandomFragment2 : Fragment(R.layout.fragment_random), SelectSimiliarDrinkListener {

    private val viewModel: RandomCocktailViewModel by viewModels()

    private var listener: SelectSimiliarDrinkListener? = null

    private val fragment: DrinkCardFragment by lazy {
        childFragmentManager.findFragmentByTag("card") as DrinkCardFragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenCreated {
            viewModel.uiState.collect { state: RandomDrinkState ->
                renderDrinkHistoryBar(state.navBarSection)
                fragment.setContent(state.drinkCardState) {
                    viewModel.onClickSimiliar(it)
                }
            }
        }

        viewModel.onStart()
    }

    private fun renderDrinkHistoryBar(section: SectionState<NavBarUi>) {
        if (section.content != null) {
            section.content.let { ui ->

                if (ui.hasNext) {
                    forwardFab.show()
                    forwardFab.setOnClickListener {
                        viewModel.onClickNextDrink()
                    }
                } else {
                    forwardFab.hide()
                }

                if (ui.hasPrev) {
                    backFab.show()
                    backFab.setOnClickListener {
                        viewModel.onClickPrevDrink()
                    }
                } else {
                    backFab.hide()
                }
            }
        } else {
            backFab.hide()
            forwardFab.hide()
        }
    }

    override fun select(drink: CocktailDto) {
        listener?.select(drink)
    }

}