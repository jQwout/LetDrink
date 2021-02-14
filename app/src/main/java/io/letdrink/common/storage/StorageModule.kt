package io.letdrink.common.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Qualifier

@Module
@InstallIn(ApplicationComponent::class)
object SharedPreferenceModule {

    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("common", Context.MODE_PRIVATE)
    }
}

@Module
@InstallIn(ApplicationComponent::class)
interface KeyValueModule {

    @Binds
    fun bind(impl: KeyValueStorageImpl): KeyValueStorage

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FavoritesKeyValeStorage