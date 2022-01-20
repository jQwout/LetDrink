package io.letdrink.features.search.by_name

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import io.letDrink.localbar.db.repository.CocktailFacade
import io.letdrink.R
import io.letdrink.common.arch.setToolbar
import io.letdrink.common.recycler.ItemAdapter
import io.letdrink.common.recycler.items.emptyItem
import io.letdrink.common.viewmodel.BaseViewModel2
import io.letdrink.features.drink_list.DrinkItem
import io.letdrink.section.drink.getDrinkDetailsActivityIntent
import kotlinx.android.synthetic.main.activity_search_screen.*
import kotlinx.android.synthetic.main.fragment_favorites.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


fun Activity.startSearchByNameActivity() =
    startActivity(Intent(this, SearchByNameActivity::class.java))

fun Context.getSearchByNameIntent(list: List<String>) {
    Intent(this, SearchByNameActivity::class.java)
        .putExtra("byNameMode", true)
        .putExtra("categories", list.toTypedArray())
}

fun Context.getSearchByIngredientIntent(list: List<String>) {
    Intent(this, SearchByNameActivity::class.java)
        .putExtra("byNameMode", false)
        .putExtra("categories", list.toTypedArray())
}


@AndroidEntryPoint
class SearchByNameActivity : AppCompatActivity(R.layout.activity_search_screen) {

    val searchByNameViewModel: SearchByNameViewModel by viewModels()

    private val byNameMode: Boolean by lazy {
        intent.getBooleanExtra("byNameMode", true)
    }

    private val categories: List<String> by lazy {
        intent.getStringArrayExtra("categories")?.toList() ?: emptyList()
    }

    private val itemAdapter: ItemAdapter<AbstractItem<*>> by lazy {
        ItemAdapter(
            list,
            LinearLayoutManager(this)
        ) { view, item ->
            if (item is DrinkItem) {
                startActivity(
                    getDrinkDetailsActivityIntent(item.drink),
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setToolbar(toolbar)

        title = "Search by name"

        lifecycleScope.launchWhenCreated {

            launch {
                searchByNameViewModel.searchState.collect {
                    when (it) {
                        is Search.Data -> itemAdapter.set(it.list)
                        is Search.NotEmpty -> itemAdapter.set(emptyItem())
                    }
                }
            }
        }

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchByNameViewModel.search(newText)
                return false
            }
        })


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}


@HiltViewModel
class SearchByNameViewModel @Inject constructor(
    private val cocktailFacade: CocktailFacade
) : BaseViewModel2() {

    val searchState = MutableStateFlow<Search>(Search.NotEmpty)

    fun search(symbols: String?) {

        backgroundScope.launch {

            if (symbols == null || symbols.isNullOrBlank() || symbols.length < 3) {
                return@launch
            }

            cocktailFacade.get()
                .map { list ->
                    list.filter { dto -> dto.data.name.contains(symbols, true) }
                        .map { DrinkItem(it) }
                }
                .collect {
                    if (it.isEmpty()) {
                        searchState.emit(Search.NotEmpty)
                    } else {
                        searchState.emit(Search.Data(it))
                    }
                }
        }
    }
}

sealed class Search {

    object NotEmpty : Search()

    class Data(val list: List<DrinkItem>) : Search()
}