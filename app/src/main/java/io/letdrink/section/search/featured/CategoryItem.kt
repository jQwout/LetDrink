package io.letdrink.section.search.featured

import java.io.Serializable

data class CategoryModel(
    val description: String,
    val enabled: Boolean,
    val items: List<String>,
    val size: Int,
    val title: String,
    var image: String? = null
) : Serializable