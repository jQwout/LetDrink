package io.letdrink.section.search.featured.recycler

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import com.bumptech.glide.Glide
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import io.letDrink.localbar.db.pojo.FeaturedItem
import io.letdrink.R
import io.letdrink.common.const.Constants.FastAdapter.CATEGORIES_RECYCLER_ID
import io.letdrink.section.search.featured.CategoryModel

open class CategoriesItem(
    var model: FeaturedItem
) : AbstractItem<CategoriesItem.ViewHolder>() {

    override val type: Int
        get() = CATEGORIES_RECYCLER_ID

    override val layoutRes: Int
        get() = R.layout.item_category

    override fun getViewHolder(v: View): ViewHolder {
        return ViewHolder(v)
    }

    class ViewHolder(private val view: View) : FastAdapter.ViewHolder<CategoriesItem>(view) {
        var name: TextView = view.findViewById(R.id.drinkCategoryDescription)
        var image: ImageView = view.findViewById(R.id.drinkCategoryImage)

        override fun bindView(item: CategoriesItem, payloads: List<Any>) {
            name.text = item.model.description

            Glide.with(view)
                .load(item.model.getImg())
                .into(image)

            ViewCompat.setTransitionName(image , item.model.description)
        }

        override fun unbindView(item: CategoriesItem) {
            name.text = null
            image.setImageDrawable(null)
        }
    }
}