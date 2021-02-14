package io.letdrink.section.search.featured

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import io.letdrink.R
import io.letdrink.common.const.Constants.FastAdapter.CATEGORIES_RECYCLER_ID

open class CategoriesItem(
    var name: String,
    var imageUrl: String
) : AbstractItem<CategoriesItem.ViewHolder>() {

    constructor(model: CategoryModel) : this(
        model.description, checkNotNull(model.image)
    )

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
            name.text = item.name

            Glide.with(view)
                .load(item.imageUrl)
                .into(image)
        }

        override fun unbindView(item: CategoriesItem) {
            name.text = null
            image.setImageDrawable(null)
        }
    }
}