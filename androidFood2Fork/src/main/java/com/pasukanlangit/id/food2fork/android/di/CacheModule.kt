package com.pasukanlangit.id.food2fork.android.di

import android.app.Application
import com.pasukanlangit.id.food2fork.datasource.cache.*
import com.pasukanlangit.id.food2fork.domain.util.DateTimeUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Provides
    @Singleton
    fun provideRecipeDatabase(application: Application): RecipeDatabase =
        RecipeDatabaseFactory(
            driverFactory = DriverFactory(context = application)
        ).crateDatabase()

    @Provides
    @Singleton
    fun provideRecipeCache(db: RecipeDatabase): RecipeCache =
        RecipeCacheImpl(
            recipeDatabase = db,
            dateTimeUtil = DateTimeUtil()
        )
}