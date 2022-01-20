package io.letDrink.localbar.db.pojo

import java.io.Serializable

data class IngredientRaw(
    val ingredient: String,
    val measure: String,
    val quantity:  String
) : Serializable


data class IngredientDto(
    val data: IngredientRaw,
    val isFavourite: Boolean
)