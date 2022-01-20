package io.letdrink.common.recycler.items

import android.view.View
import android.widget.TextView
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import io.letdrink.R
import io.letdrink.common.const.Constants

class TextItem(val string: String) : AbstractItem<TextItem.ViewHolder>() {

    override val type: Int
        get() = Constants.FastAdapter.TEXT_ITEM_ID

    override val layoutRes: Int
        get() = R.layout.simple_text_item

    override fun getViewHolder(v: View): TextItem.ViewHolder {
        return ViewHolder(v)
    }

    override var identifier: Long = Long.MIN_VALUE


    class ViewHolder(view: View) : FastAdapter.ViewHolder<TextItem>(view) {

        override fun bindView(item: TextItem, payloads: List<Any>) {
            itemView.findViewById<TextView>(R.id.text).text = item.string
        }

        override fun unbindView(item: TextItem) {
            itemView.findViewById<TextView>(R.id.text).text = ""
        }
    }
}