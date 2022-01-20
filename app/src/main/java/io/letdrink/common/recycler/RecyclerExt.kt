package io.letdrink.common.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.ClickListener
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.GenericItem
import com.mikepenz.fastadapter.adapters.ItemAdapter

fun <T : GenericItem> ItemAdapter(
    recyclerView: RecyclerView,
    layoutManager: RecyclerView.LayoutManager,
    clickListener: ClickListener<T>? = null
): ItemAdapter<T> {
    val itemAdapter = ItemAdapter<T>()
    val fastAdapter = FastAdapter.with(itemAdapter)
    fastAdapter.onClickListener = clickListener
    recyclerView.adapter = fastAdapter
    recyclerView.layoutManager = layoutManager
    return itemAdapter
}

fun <T : GenericItem> ItemAdapter(
    recyclerView: RecyclerView,
    layoutManager: RecyclerView.LayoutManager,
    clickListener: (T) -> Unit
): ItemAdapter<T> {
    val itemAdapter = ItemAdapter<T>()
    val fastAdapter = FastAdapter.with(itemAdapter)
    fastAdapter.onClickListener = { _, _, item, _ ->
        clickListener(item)
        true
    }
    recyclerView.adapter = fastAdapter
    recyclerView.layoutManager = layoutManager
    return itemAdapter
}

fun <T : GenericItem> ItemAdapter(
    recyclerView: RecyclerView,
    layoutManager: RecyclerView.LayoutManager,
    clickListener: (View, T) -> Unit
): ItemAdapter<T> {
    val itemAdapter = ItemAdapter<T>()
    val fastAdapter = FastAdapter.with(itemAdapter)
    fastAdapter.onClickListener = { view, _, item, _ ->
        clickListener(view!!, item)
        true
    }
    recyclerView.adapter = fastAdapter
    recyclerView.layoutManager = layoutManager
    return itemAdapter
}