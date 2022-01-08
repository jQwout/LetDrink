package io.letdrink.features.config

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ConfigModule {

    @Provides
    fun config(): Config {
        return Config()
    }

}