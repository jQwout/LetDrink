package io.letdrink.features.favorites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.diff.FastAdapterDiffUtil
import dagger.hilt.android.AndroidEntryPoint
import io.letdrink.R
import io.letdrink.common.recycler.ItemAdapter
import io.letdrink.features.drink_list.DrinkItem
import io.letdrink.section.drink.getDrinkDetailsActivityIntent
import kotlinx.android.synthetic.main.fragment_favorites.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private val itemAdapter: ItemAdapter<DrinkItem> by lazy {
        favoritesDrinks.itemAnimator = null
        ItemAdapter(
            favoritesDrinks,
            LinearLayoutManager(requireContext())
        ) { drink ->
            startActivity(requireActivity().getDrinkDetailsActivityIntent(drink.drink))
        }
    }

    private val viewModel: FavoritesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is FavoritesUIState.Content -> {
                        setContent(state.drinks)
                    }
                    is FavoritesUIState.Loading -> Unit
                    is FavoritesUIState.Empty -> {
                        setEmpty()
                    }
                }
            }
        }

        viewModel.loadFavorites()
    }

    private fun setContent(drinks: List<DrinkItem>) {
        favoritesEmpty.visibility = View.INVISIBLE
        favoritesDrinks.visibility = View.VISIBLE
        FastAdapterDiffUtil[itemAdapter] = FastAdapterDiffUtil.calculateDiff(itemAdapter, drinks)
    }

    private fun setEmpty() {
        favoritesEmpty.visibility = View.VISIBLE
        favoritesDrinks.visibility = View.INVISIBLE
    }
}