package io.letdrink.section.search.featured.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import io.letdrink.common.storage.FilesStorage
import io.letdrink.section.search.featured.FeaturedApi
import io.letdrink.section.search.featured.FeaturedRepository

@Module
@InstallIn(ActivityComponent::class)
object FeaturedModule {

    @Provides
    fun provideDefaultFeatured(api: FeaturedApi, filesStorage: FilesStorage): FeaturedRepository {
        return FeaturedRepository(api, filesStorage)
    }
}