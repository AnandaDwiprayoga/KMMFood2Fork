package com.pasukanlangit.id.food2fork.datasource.cache

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver =
        AndroidSqliteDriver(RecipeDatabase.Schema, context, RecipeDatabaseFactory.RECIPE_DATABASE_NAME)
}