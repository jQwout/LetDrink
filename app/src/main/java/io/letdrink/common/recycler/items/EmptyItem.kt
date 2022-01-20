package io.letdrink.common.recycler.items

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import io.letdrink.R
import io.letdrink.common.const.Constants
import io.letdrink.features.drink_list.DrinkItem

class EmptyItem : AbstractItem<EmptyItem.ViewHolder>() {

    override val type: Int
        get() = Constants.FastAdapter.EMPTY_ITEM_ID

    override val layoutRes: Int
        get() = R.layout.item_empty

    override fun getViewHolder(v: View): EmptyItem.ViewHolder {
        return ViewHolder(v)
    }

    override var identifier: Long = Long.MIN_VALUE


    class ViewHolder(view: View) : FastAdapter.ViewHolder<EmptyItem>(view) {

        override fun bindView(item: EmptyItem, payloads: List<Any>) {
        }

        override fun unbindView(item: EmptyItem) {
        }
    }
}

fun emptyItem() = listOf(EmptyItem())