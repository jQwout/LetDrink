package io.letdrink.section.search.featured.category

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.thecocktaildb.network.models.Drink
import com.mikepenz.fastadapter.adapters.ItemAdapter
import dagger.hilt.android.AndroidEntryPoint
import io.letDrink.localbar.db.CocktailRepository
import io.letdrink.R
import io.letdrink.common.recycler.ItemAdapter
import io.letdrink.common.utils.intent
import io.letdrink.common.viewmodel.BaseViewModel
import io.letdrink.features.drink_list.DrinkItem
import io.letdrink.features.glide.RequestListenerBase
import io.letdrink.features.mapper.toDrink
import io.letdrink.section.drink.getDrinkDetailsActivityIntent
import io.letdrink.section.search.featured.CategoryModel
import kotlinx.android.synthetic.main.fragment_category_drinks.*
import kotlinx.android.synthetic.main.fragment_category_drinks_content.*
import kotlinx.android.synthetic.main.fragment_favorites.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


fun Activity.getCategoryActivityIntent(model: CategoryModel, imageTransitionName: String): Intent {
    return intent(CategoryActivity::class.java) {
        putExtra("model", model)
        putExtra("tr_name", imageTransitionName)
    }
}

@AndroidEntryPoint
class CategoryActivity : AppCompatActivity(R.layout.fragment_category_drinks) {

    private val viewModel: CategoryViewModel by viewModels()

    private val categoryModel: CategoryModel by lazy {
        intent.getSerializableExtra("model") as CategoryModel
    }

    private val drinksAdapter: ItemAdapter<DrinkItem> by lazy {
        ItemAdapter(recyclerFromCategory, LinearLayoutManager(this)) { drink ->
            startActivity(this.getDrinkDetailsActivityIntent(drink.drink))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        transition()

        lifecycleScope.launchWhenCreated {

            viewModel.uiState.collect {
                drinksAdapter.add(it.drinks)
            }
        }

        viewModel.onStart(categoryModel.items)
    }

    private fun transition() {
        collapsedToolbar.setCollapsedTitleTextColor(Color.WHITE)
        collapsedToolbar.setExpandedTitleColor(Color.WHITE)
        supportPostponeEnterTransition()
        val extras = checkNotNull(intent.extras)
        val item: CategoryModel = extras.getSerializable("model") as CategoryModel
        val imageUrl: String = checkNotNull(item.image)
        val imageTransitionName = extras.getString("tr_name")
        categoryHeaderImage.transitionName = imageTransitionName
        toolbar.title = item.title

        Glide.with(this)
            .load(imageUrl)
            .addListener(
                RequestListenerBase(
                    onReady = {
                        categoryHeaderImage.setImageDrawable(it)
                        supportStartPostponedEnterTransition()
                    },
                    onLoadFailed = { supportStartPostponedEnterTransition() })
            )
            .submit()
    }
}


class CategoryViewModel @ViewModelInject constructor(private val repository: CocktailRepository) :
    BaseViewModel<CategoryState>() {

    override val uiState: MutableStateFlow<CategoryState> = MutableStateFlow(CategoryState())

    fun onStart(names: List<String>) = backgroundScope.launch {

        val list = names.map { name ->
            repository.getByLocalName(name).map {
                DrinkItem(it.toDrink())
            }
        }.flatten()

        uiState.emit(
            CategoryState(list)
        )
    }

}

class CategoryState(
    val drinks: List<DrinkItem> = emptyList()
)