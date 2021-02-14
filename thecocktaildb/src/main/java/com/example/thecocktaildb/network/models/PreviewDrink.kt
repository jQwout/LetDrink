package com.example.thecocktaildb.network.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class PreviewDrink(
    @SerializedName("idDrink")
    val id: Long,
    @SerializedName("drinkName")
    val drinkName: String,
    val drinkThumb: String
): Serializable {
    override fun toString(): String {
        return "Drink(id=$id, drinkName=\"$drinkName\")"
    }

    fun getPreview() = "$drinkThumb/preview"
}