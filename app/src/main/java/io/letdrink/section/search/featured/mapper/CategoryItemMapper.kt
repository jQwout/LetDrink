package io.letdrink.section.search.featured.mapper

import io.letDrink.localbar.db.pojo.FeaturedItem
import io.letdrink.section.search.featured.recycler.CategoriesItem
import javax.inject.Inject

class CategoryItemMapper @Inject constructor() {

    fun map(list: List<FeaturedItem>): List<CategoriesItem> {
        return list.map { CategoriesItem(it) }
    }
}