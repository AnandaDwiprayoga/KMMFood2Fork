package com.pasukanlangit.id.food2fork.datasource.di

import com.pasukanlangit.id.food2fork.datasource.cache.*
import com.pasukanlangit.id.food2fork.domain.util.DateTimeUtil

class CacheModule {
    private val driverFactory: DriverFactory by lazy {
        DriverFactory()
    }

    val recipeDatabase: RecipeDatabase by lazy {
        RecipeDatabaseFactory(
            driverFactory = driverFactory
        ).crateDatabase()
    }
    val recipeCache: RecipeCache by lazy {
        RecipeCacheImpl(
            recipeDatabase = recipeDatabase,
            dateTimeUtil = DateTimeUtil()
        )
    }
}