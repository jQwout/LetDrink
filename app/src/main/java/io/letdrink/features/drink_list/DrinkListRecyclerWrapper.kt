package io.letdrink.features.drink_list

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import io.letdrink.common.recycler.ItemAdapter
import java.util.*

class DrinkListRecyclerWrapper(
    private val recyclerView: RecyclerView,
    private val layoutManager: LinearLayoutManager
) {

    private val itemAdapter: ItemAdapter<DrinkItem> by lazy {
        ItemAdapter(recyclerView, layoutManager)
    }

    fun addNewItems(items: List<DrinkItem>) {
        itemAdapter.clear()
        itemAdapter.add(items)
    }

    fun addItems(items: List<DrinkItem>) {
        itemAdapter.add(items)
    }
}