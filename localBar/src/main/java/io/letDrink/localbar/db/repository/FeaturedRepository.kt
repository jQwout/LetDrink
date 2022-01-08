package io.letDrink.localbar.db.repository

import io.letDrink.localbar.db.github.OpenDrinkGithubApi
import io.letDrink.localbar.db.pojo.FeaturedItem

class FeaturedRepository(private val api: OpenDrinkGithubApi) {

    suspend fun getFeatured(): List<FeaturedItem> {
        return api.getFeatured()
    }
}