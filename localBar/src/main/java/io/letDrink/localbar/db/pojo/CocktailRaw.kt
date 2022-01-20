package io.letDrink.localbar.db.pojo

import java.io.Serializable

data class CocktailRaw(
    val description: String,
    val directions: List<String>,
    val github: String,
    private val image: String,
    val keywords: List<String>,
    val name: String,
    val source: String?,
    val ingredients: List<IngredientRaw>
) : Serializable {

    fun getImg() = "${PATH_TO_IMAGE}${image}"

}

data class CocktailDto(
    val data: CocktailRaw,
    val isFavourite: Boolean
) : Serializable

data class FeaturedItem(
    val description: String,
    val enabled: Boolean,
    val items: List<String>,
    val size: Int,
    val title: String,
) : Serializable {

    fun getImg() = "${PATH_TO_IMAGE}${items.first()}.jpg"

}

const val PATH_TO_IMAGE =
    "https://raw.githubusercontent.com/alfg/opendrinks/master/src/assets/recipes/"
