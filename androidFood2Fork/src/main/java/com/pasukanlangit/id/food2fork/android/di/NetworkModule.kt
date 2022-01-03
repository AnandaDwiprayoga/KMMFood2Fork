package com.pasukanlangit.id.food2fork.android.di

import com.pasukanlangit.id.food2fork.datasource.network.KtorClientFactory
import com.pasukanlangit.id.food2fork.datasource.network.RecipeService
import com.pasukanlangit.id.food2fork.datasource.network.RecipeServiceImpl
import com.pasukanlangit.id.food2fork.datasource.network.RecipeServiceImpl.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient =
        KtorClientFactory().build()

    @Provides
    @Singleton
    fun provideRecipeService(httpClient: HttpClient): RecipeService =
        RecipeServiceImpl(
            httpClient = httpClient,
            baseUrl = BASE_URL
        )
}