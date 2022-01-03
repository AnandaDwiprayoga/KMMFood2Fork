package com.pasukanlangit.id.food2fork.datasource.cache

import com.squareup.sqldelight.db.SqlDriver

class RecipeDatabaseFactory(
    private val driverFactory: DriverFactory
) {
    fun crateDatabase(): RecipeDatabase =
        RecipeDatabase(driver = driverFactory.createDriver())

    companion object {
        const val RECIPE_DATABASE_NAME = "recipes.db"
    }
}

expect class DriverFactory {
    fun createDriver(): SqlDriver
}

