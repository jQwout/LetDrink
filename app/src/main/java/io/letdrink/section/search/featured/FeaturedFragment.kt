package io.letdrink.section.search.featured

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.adapters.ItemAdapter
import dagger.hilt.android.AndroidEntryPoint
import io.letdrink.R
import io.letdrink.common.DataFlowEvent
import io.letdrink.common.DataFlowSource
import io.letdrink.common.const.Constants
import io.letdrink.common.recycler.ItemAdapter
import io.letdrink.common.state.SectionState
import io.letdrink.common.viewmodel.BaseViewModel
import kotlinx.android.synthetic.main.fragment_featured_drink.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class FeaturedFragment : Fragment(R.layout.fragment_featured_drink) {

    private val viewModel:FeaturedViewModel by viewModels()

    private val categoriesAdapter: ItemAdapter<CategoriesItem> by lazy {
        ItemAdapter(
            categoryRecyclerView,
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false),
        ) { v, adapter, item, position ->

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

        viewModel.create()
    }

    fun setContent(sectionState: SectionState<List<CategoryModel>>) {
        if (sectionState.isLoading) {
            categoryContainer.displayedChild = 0
        } else {
            categoryContainer.displayedChild = 1
            sectionState.content?.let { list ->
                list.map {
                    categoriesAdapter.add(CategoriesItem(it))
                }
            }
        }
    }
}

class FeaturedViewModel @ViewModelInject constructor(private val featuredRepository: FeaturedRepository) :
    BaseViewModel<FeaturedViewState>() {

    override val uiState: MutableStateFlow<FeaturedViewState> = MutableStateFlow(
        FeaturedViewState(
            SectionState(isLoading = true)
        )
    )

    fun create() {
        backgroundScope.launch {
            featuredRepository.get().collect {
                when (it) {
                    is DataFlowEvent.Loading -> {
                        if (it.source == DataFlowSource.REMOTE) {
                            uiState.emit(
                                FeaturedViewState(
                                    SectionState(isLoading = true)
                                )
                            )
                        }
                    }
                    is DataFlowEvent.Content -> {
                        uiState.emit(
                            FeaturedViewState(
                                SectionState(it.value.extractIconForCategory(),isLoading = false)
                            )
                        )
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun List<CategoryModel>.extractIconForCategory(): List<CategoryModel> {
        return map {
            it.copy(image = Constants.LOCAL_BAR.IMAGES + it.items.random() + ".jpg")
        }
    }
}

class FeaturedViewState(val categories: SectionState<List<CategoryModel>>)