package io.letdrink.features.drink_list

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import io.letDrink.localbar.db.pojo.CocktailRaw
import io.letdrink.R
import io.letdrink.common.const.Constants

open class DrinkItem(val drink: CocktailRaw) : AbstractItem<DrinkItem.ViewHolder>() {

    override val type: Int
        get() = Constants.FastAdapter.DRINK_ITEM_ID

    override val layoutRes: Int
        get() = R.layout.item_drink_vertical

    override fun getViewHolder(v: View): ViewHolder {
        return ViewHolder(v)
    }

    override var identifier: Long = drink.name.hashCode().toLong()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as DrinkItem

        if (drink != other.drink) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + drink.hashCode()
        return result
    }


    class ViewHolder(view: View) : FastAdapter.ViewHolder<DrinkItem>(view) {
        var name: TextView = view.findViewById(R.id.name)
        var imagePreview: ImageView = view.findViewById(R.id.imagePreview)
        val description: TextView = view.findViewById(R.id.description)

        override fun bindView(item: DrinkItem, payloads: List<Any>) {
            name.text = item.drink.name
            description.text = item.drink.description

            Glide.with(imagePreview.context)
                .load(item.drink.getImg())
                .into(imagePreview)
        }

        override fun unbindView(item: DrinkItem) {
            name.text = null
            description.text = null
            imagePreview.setImageDrawable(null)
        }
    }
}