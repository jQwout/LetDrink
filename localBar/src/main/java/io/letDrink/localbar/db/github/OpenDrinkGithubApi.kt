package io.letDrink.localbar.db.github

import io.letDrink.localbar.db.pojo.CocktailRaw
import io.letDrink.localbar.db.pojo.CocktailsPreviewRaw
import io.letDrink.localbar.db.pojo.FeaturedItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface OpenDrinkGithubApi {

    @GET("recipes/{name}.json")
    suspend fun getCocktail(@Path("name") name: String): CocktailRaw

    @GET("featured.json")
    suspend fun getFeatured() : List<FeaturedItem>

    @GET()
    suspend fun getCocktailRaw(@Url url: String): CocktailRaw

    @GET()
    suspend fun getCocktails(
        @Url url: String
    ): List<CocktailsPreviewRaw>

    companion object {
        val RAW_URL = "https://raw.githubusercontent.com/alfg/opendrinks/master/src/"
        val STORAGE_RAW_URL = "https://api.github.com/repos/alfg/opendrinks/contents/src/recipes"
    }
}