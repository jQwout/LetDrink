package io.letdrink.section.search.featured.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import io.letdrink.common.storage.FilesStorage
import io.letdrink.section.search.featured.FeaturedApi
import io.letdrink.section.search.featured.FeaturedRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ActivityComponent::class)
object FeaturedModule {

    @Provides
    fun provideDefaultFeatured(api: FeaturedApi, filesStorage: FilesStorage): FeaturedRepository {
        return FeaturedRepository(api, filesStorage)
    }

    @Provides
    fun provideApi(client: OkHttpClient, gson: Gson): FeaturedApi {
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("https://raw.githubusercontent.com/alfg/opendrinks/master/src/")
            .build()
            .create(FeaturedApi::class.java)
    }

}