package io.letdrink.section.drink

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.github.florent37.fiftyshadesof.FiftyShadesOf
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import com.mikepenz.fastadapter.IAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import dagger.hilt.android.AndroidEntryPoint
import io.letDrink.localbar.db.pojo.CocktailDto
import io.letdrink.R
import io.letdrink.common.const.Constants
import io.letdrink.common.recycler.ItemAdapter
import io.letdrink.common.state.SectionState
import io.letdrink.common.utils.BottomSheetBehaviorCallback
import io.letdrink.common.utils.MetricsUtil
import io.letdrink.common.utils.visibleOrGone
import io.letdrink.features.drink_list.PreviewDrinkItem
import io.letdrink.features.ingredient.adapter.IngradientsItem
import kotlinx.android.synthetic.main.fragment_drink_card_bottom_sheet_redesing.*
import kotlinx.android.synthetic.main.fragment_drink_card_bottom_sheet_redesing.similiar
import kotlinx.android.synthetic.main.fragment_drink_card_bottom_sheet_redesing_shimmer.*
import kotlinx.android.synthetic.main.fragment_drink_card_redesing.*
import kotlinx.android.synthetic.main.fragment_random.*
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class DrinkCardFragment : Fragment(R.layout.fragment_drink_card_redesing) {

    private val viewModel: DrinkCardViewModel by viewModels()

    private val itemAdapter: ItemAdapter<IngradientsItem> by lazy {
        ItemAdapter(
            ingredients, LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        )
    }

    private val similiarAdapter: ItemAdapter<PreviewDrinkItem> by lazy {
        ItemAdapter(
            similiar,
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false),
            clickListener = { v: View?, adapter: IAdapter<PreviewDrinkItem>, item: PreviewDrinkItem, position: Int ->
                listener?.select(item.drink)
                true
            }
        )
    }

    var listener: SelectSimiliarDrinkListener? = null

    private val bottomSheetBehavior: BottomSheetBehavior<*>?
        get() {
            if (drinkCardBottomSheet == null) return null

            return BottomSheetBehavior.from(drinkCardBottomSheet).apply {
                addBottomSheetCallback(BottomSheetBehaviorCallback {
                    if (BottomSheetBehavior.STATE_EXPANDED == it) {
                        favouriteIcon.visibility = View.VISIBLE
                    } else {
                        favouriteIcon.visibility = View.GONE
                    }
                })
            }
        }

    private val drinkLoadingDelegate: DrinkLoadingDelegate?
        get() {
            if (drinkCardBottomSheet == null) return null
            return DrinkLoadingDelegate(drinkCardBottomSheet)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenResumed {
            viewModel.uiState.collect { state ->
                state.favourite.content?.let { setFavorite(it) }
             //   setSimiliar(state.similiar)
            }
        }
    }

    fun setLoading() {
        drinkLoadingDelegate?.bind()
        bottomSheetBehavior?.apply {
            state = BottomSheetBehavior.STATE_COLLAPSED
            peekHeight = getMinHeightBottomSheetForLoading()
        }
        FiftyShadesOf.with(requireContext())
            .on(shimmerDrinkBottomFrame)
            .start()
    }

    fun stopLoading() {
        FiftyShadesOf.with(requireContext())
            .on(shimmerDrinkBottomFrame)
            .stop()
    }

    private fun View.setHeight(height: Int) {
        val params = layoutParams
        params.height = height
        layoutParams = params
    }

    fun setContent(
        state: SectionState<CocktailDto>,
        listener: SelectSimiliarDrinkListener
    ) {

        state.content?.let { d ->
            val drink = d.data

            // drinkLoadingDelegate?.showContentIfNeed()

            if (drink.name != name.text) {

                name.text = drink.name
                instructionDescription.text = drink.directions.joinToString("\n")
                itemAdapter.clear()
                itemAdapter.add(drink.ingredients.map { i ->
                    IngradientsItem(
                        i.ingredient,
                        i.quantity
                    )
                })

                Glide.with(image.context)
                    .load(drink.getImg())
                    .addListener(object : RequestListener<Drawable> {

                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable,
                            model: Any?,
                            target: Target<Drawable>,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            val margin =
                                MetricsUtil.convertDpToPixel(requireContext(), 16f).toInt()
                            image.setHeight(drinkCardRedesingFragment.width)
                            bottomSheetBehavior?.setPeekHeight(
                                drinkCardRedesingFragment.height - drinkCardRedesingFragment.width + margin,
                                true
                            )
                            image.setImageDrawable(resource)
                            return true
                        }
                    })
                    .into(image)

                similiarAdapter.clear()
                viewModel.loadSimiliar(d)
            }

            setCategories(drink.keywords)

            setFavorite(d)

            this.listener = listener
        }

        //    if (state.isLoading) setLoading() else stopLoading()
        // todo
    }

    fun setSimiliar(state: SectionState<List<CocktailDto>>) {

        if (state.isLoading) {
            similiarTitle.visibility = View.GONE
            similiar.visibility = View.GONE
            similiarShimmer.visibility = View.VISIBLE
            FiftyShadesOf.with(requireContext())
                .on(similiarShimmer)
                .start()

        } else {
            similiarShimmer.visibility = View.GONE
            FiftyShadesOf.with(requireContext())
                .on(similiarShimmer)
                .stop()
        }

        state.content?.let { p ->
            if (p.isNotEmpty()) {
                similiarTitle.visibility = View.VISIBLE
                similiar.visibility = View.VISIBLE
                similiarAdapter.add(p.map { PreviewDrinkItem(it) })
            }
        }

    }

    fun setFavorite(drink: CocktailDto) {

        favoriteFab.setImageResource(
            if (drink.isFavourite) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24
        )

        favouriteIcon.setImageResource(
            if (drink.isFavourite) R.drawable.ic_baseline_favorite_24_black else R.drawable.ic_baseline_favorite_border_black_24
        )

        favoriteFab.setOnClickListener {
            viewModel.changeFavorite(drink)
        }

        favouriteIcon.setOnClickListener {
            viewModel.changeFavorite(drink)
        }
    }

    private fun setCategories(list: List<String>) {
        categoryChips.removeAllViews()
        list.forEach {
            val c = Chip(requireContext())
            c.text = it
            categoryChips.addView(c)
        }
    }

    private fun getMinHeightBottomSheetForLoading(): Int {
        return (MetricsUtil.getDisplayH(requireActivity()) * 0.5).toInt()
    }

    companion object {
        fun create(transition: String): DrinkCardFragment {
            val d = DrinkCardFragment()
            d.arguments = bundleOf(Constants.EXTRA.TRANSACTION to transition)
            return d
        }
    }
}

