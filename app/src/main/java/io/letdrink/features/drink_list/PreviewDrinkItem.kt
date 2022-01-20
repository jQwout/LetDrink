package io.letdrink.features.drink_list

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import io.letDrink.localbar.db.pojo.CocktailDto
import io.letDrink.localbar.db.pojo.CocktailRaw
import io.letdrink.R
import io.letdrink.common.const.Constants

class PreviewDrinkItem(val drink: CocktailDto) : AbstractItem<PreviewDrinkItem.ViewHolder>() {

    override val type: Int
        get() = Constants.FastAdapter.PREVIEW_DRINK_ITEM_ID

    override val layoutRes: Int
        get() = R.layout.item_preview_drink

    override fun getViewHolder(v: View): ViewHolder {
        return ViewHolder(v)
    }

    class ViewHolder(view: View) : FastAdapter.ViewHolder<PreviewDrinkItem>(view) {
        var name: TextView = view.findViewById(R.id.name)
        var imagePreview: ImageView = view.findViewById(R.id.imagePreview)
        val ingredients: TextView = view.findViewById(R.id.ingredients)

        override fun bindView(item: PreviewDrinkItem, payloads: List<Any>) {
            name.text = item.drink.data.name
            ingredients.text = item.drink.data.ingredients.joinToString(",") { it.ingredient }

            Glide.with(imagePreview.context)
                .load(item.drink.data.getImg())
                .into(imagePreview)
        }

        override fun unbindView(item: PreviewDrinkItem) {
            name.text = null
            imagePreview.setImageDrawable(null)
        }
    }
}

class PreviewDrinkItemPlaceholder() : AbstractItem<PreviewDrinkItemPlaceholder.ViewHolder>() {

    override val type: Int
        get() = Constants.FastAdapter.PREVIEW_DRINK_ITEM_PH_ID

    override val layoutRes: Int
        get() = R.layout.item_preview_drink_placeholder

    override fun getViewHolder(v: View): ViewHolder {
        return ViewHolder(v)
    }

    class ViewHolder(view: View) : FastAdapter.ViewHolder<PreviewDrinkItemPlaceholder>(view) {
        override fun bindView(item: PreviewDrinkItemPlaceholder, payloads: List<Any>) {

        }

        override fun unbindView(item: PreviewDrinkItemPlaceholder) {

        }
    }
}

