package io.letdrink.features.search.by_category

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mikepenz.fastadapter.adapters.ItemAdapter
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import io.letDrink.localbar.db.repository.CategoryRepository
import io.letdrink.R
import io.letdrink.common.arch.setToolbar
import io.letdrink.common.recycler.ItemAdapter
import io.letdrink.common.recycler.items.TextItem
import io.letdrink.common.recycler.items.emptyItem
import io.letdrink.common.utils.visibleOrGone
import io.letdrink.common.viewmodel.BaseViewModel2
import io.letdrink.features.search.by_name.Search
import kotlinx.android.synthetic.main.activity_search_screen.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

fun Activity.startSearchByNameActivity() =
    startActivity(Intent(this, SearchByCategory::class.java))

@AndroidEntryPoint
class SearchByCategory : AppCompatActivity(R.layout.activity_search_screen) {

    private val viewModel: SearchByCategoryViewModel by viewModels()

    private val itemAdapter: ItemAdapter<TextItem> by lazy {
        ItemAdapter(
            list,
            LinearLayoutManager(this)
        ) { item ->

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setToolbar(toolbar)

        title = "Search by category"

        lifecycleScope.launchWhenCreated {

            launch {

            }
        }

        search.visibleOrGone(false)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}


@HiltViewModel
class SearchByCategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : BaseViewModel2() {

    val categoriesItems = categoryRepository.keywords.map { list ->
        list.map { TextItem(it) }
    }
}