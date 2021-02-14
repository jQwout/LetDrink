package io.letdrink.features.ingredient.adapter

import android.view.View
import android.widget.TextView
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import io.letdrink.R
import io.letdrink.common.const.Constants.FastAdapter.INGRADIENTS_ITEM_ID

open class IngradientsItem(
    var name: String? = null,
    var amount: String? = null
) : AbstractItem<IngradientsItem.ViewHolder>() {

    override val type: Int
        get() = INGRADIENTS_ITEM_ID

    override val layoutRes: Int
        get() = R.layout.ingredient_item_binding

    override fun getViewHolder(v: View): ViewHolder {
        return ViewHolder(v)
    }

    class ViewHolder(view: View) : FastAdapter.ViewHolder<IngradientsItem>(view) {
        var name: TextView = view.findViewById(R.id.ingredientName)
        var amount: TextView = view.findViewById(R.id.ingredientAmount)

        override fun bindView(item: IngradientsItem, payloads: List<Any>) {
            name.text = item.name
            amount.text = item.amount
        }

        override fun unbindView(item: IngradientsItem) {
            name.text = null
            amount.text = null
        }
    }
}