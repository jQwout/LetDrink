package io.letdrink.section.search.featured

import retrofit2.http.GET

interface FeaturedApi {

    @GET("featured.json")
    suspend fun getListCategories() : List<CategoryModel>
}