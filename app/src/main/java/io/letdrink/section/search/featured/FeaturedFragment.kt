package io.letdrink.section.search.featured

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.adapters.ItemAdapter
import dagger.hilt.android.AndroidEntryPoint
import io.letDrink.localbar.db.pojo.FeaturedItem
import io.letdrink.R
import io.letdrink.common.recycler.ItemAdapter
import io.letdrink.common.state.SectionState
import io.letdrink.features.search.by_name.startSearchByNameActivity
import io.letdrink.section.search.featured.category.getCategoryActivityIntent
import io.letdrink.section.search.featured.recycler.CategoriesItem
import kotlinx.android.synthetic.main.fragment_featured_drink.*
import kotlinx.android.synthetic.main.search_category_block.*
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class FeaturedFragment : Fragment(R.layout.fragment_featured_drink) {

    private val viewModel: FeaturedViewModel by viewModels()

    private val categoriesAdapter: ItemAdapter<CategoriesItem> by lazy {
        ItemAdapter(
            categoryRecyclerView,
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false),
        ) { v, adapter, item, position ->
            val image = checkNotNull(v?.findViewById<ImageView>(R.id.drinkCategoryImage))
            startCategoryActivity(image, item.model)
            true
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenCreated {
            viewModel.uiState.collect {
                setContent(it.categories)
            }
        }

        byName.setOnClickListener {
            requireActivity().startSearchByNameActivity()
        }

        viewModel.create()
    }

    fun setContent(sectionState: SectionState<List<CategoriesItem>>) {
        if (sectionState.isLoading) {
            categoryContainer.displayedChild = 0
        } else {
            categoryContainer.displayedChild = 1
            sectionState.content?.let { list ->
                categoriesAdapter.add(list)
            }
        }
    }

    private fun startCategoryActivity(imageView: ImageView, featuredItem: FeaturedItem) {
        val transitionName = ViewCompat.getTransitionName(imageView)!!
        val options: ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
            requireActivity(),
            imageView,
            transitionName
        )

        startActivity(
            requireActivity().getCategoryActivityIntent(featuredItem, transitionName),
            options.toBundle()
        )
    }
}

class FeaturedViewState(val categories: SectionState<List<CategoriesItem>>)