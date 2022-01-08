package io.letdrink.common.storage

import android.content.Context
import android.content.SharedPreferences
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferenceModule {

    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("common", Context.MODE_PRIVATE)
    }
}

@Module
@InstallIn(SingletonComponent::class)
interface KeyValueModule {

    @Binds
    fun bind(impl: KeyValueStorageImpl): KeyValueStorage

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FavoritesKeyValeStorage