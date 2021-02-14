package com.example.thecocktaildb.network.models

import java.io.Serializable

data class Drink(
    val id: Long,
    val drinkName: String,
    val tags: String?,
    val instructions: String,
    val categories: String?,
    val description: String?,
    val alcoholic: String,
    val ingredients: List<Ingredient>,
    val imageSource: String?,
    val drinkThumb: String,
    val isFavourite: Boolean,
) : Serializable {

    override fun toString(): String {
        return "Drink(id=$id, drinkName=\"$drinkName\", tags=\"$tags\", instructions=\"$instructions\", ingredients=$ingredients, imageSource=$imageSource, drinkThumb=\"$drinkThumb\")"
    }

    fun getPreview() = "$drinkThumb/preview"

    fun getDescriptionOnIngredientsStr(): String {
        if (description.isNullOrBlank()) {
            return ingredients.map { it.name }.reduce { acc, s -> "$acc, $s" }
        }
        return description
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Drink

        if (id != other.id) return false
        if (drinkName != other.drinkName) return false
        if (tags != other.tags) return false
        if (instructions != other.instructions) return false
        if (categories != other.categories) return false
        if (description != other.description) return false
        if (alcoholic != other.alcoholic) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + drinkName.hashCode()
        result = 31 * result + (tags?.hashCode() ?: 0)
        result = 31 * result + instructions.hashCode()
        result = 31 * result + (categories?.hashCode() ?: 0)
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + alcoholic.hashCode()
        return result
    }


    class Ingredient(val name: String, val count: String) : Serializable
}
