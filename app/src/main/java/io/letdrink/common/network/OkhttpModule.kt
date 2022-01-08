package io.letdrink.common.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.letDrink.localbar.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object OkhttpModule {

    private const val HTTP_TIMEOUT_MS = 5000

    @Provides
    fun createOkHttp(): OkHttpClient {
        val builder = OkHttpClient.Builder().also {
            it.connectTimeout(HTTP_TIMEOUT_MS.toLong(), TimeUnit.MILLISECONDS)
            it.readTimeout(HTTP_TIMEOUT_MS.toLong(), TimeUnit.MILLISECONDS)
            it.writeTimeout(HTTP_TIMEOUT_MS.toLong(), TimeUnit.MILLISECONDS)
            it.followRedirects(false)
        }

        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            builder.addInterceptor(interceptor)
        }

        return builder.build()
    }

}