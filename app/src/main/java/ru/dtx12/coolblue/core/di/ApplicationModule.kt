package ru.dtx12.coolblue.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.dtx12.coolblue.BuildConfig
import ru.dtx12.coolblue.core.data.endpoints.SearchApi
import ru.dtx12.coolblue.core.data.repositories.ProductsRepositoryImpl
import ru.dtx12.coolblue.core.domain.repositories.ProductsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    fun provideApi(): SearchApi {
        return Retrofit.Builder()
            .baseUrl("https://bdk0sta2n0.execute-api.eu-west-1.amazonaws.com/")
            .client(createClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SearchApi::class.java)
    }

    private fun createClient(): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }
        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideProductsRepository(impl: ProductsRepositoryImpl): ProductsRepository = impl
}