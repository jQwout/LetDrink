package io.letDrink.localbar.db.pojo

import com.google.gson.annotations.SerializedName

class CocktailsPreviewRaw(
    val name: String,
    @SerializedName("download_url")
    val link: String
)