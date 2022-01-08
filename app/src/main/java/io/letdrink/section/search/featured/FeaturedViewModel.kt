package io.letdrink.section.search.featured

import dagger.hilt.android.lifecycle.HiltViewModel
import io.letDrink.localbar.db.repository.FeaturedRepository
import io.letdrink.common.state.SectionState
import io.letdrink.common.viewmodel.BaseViewModel
import io.letdrink.section.search.featured.mapper.CategoryItemMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeaturedViewModel @Inject constructor(
    private val featuredRepository: FeaturedRepository,
    private val mapper: CategoryItemMapper
) : BaseViewModel<FeaturedViewState>() {

    override val uiState: MutableStateFlow<FeaturedViewState> = MutableStateFlow(
        FeaturedViewState(
            SectionState(isLoading = true)
        )
    )

    fun create() {
        backgroundScope.launch {
            uiState.emit(FeaturedViewState(SectionState(isLoading = true)))
            uiState.emit(
                FeaturedViewState(
                    SectionState(
                        mapper.map(
                            featuredRepository.getFeatured()
                        ),
                        isLoading = false
                    )
                )
            )
        }
    }
}