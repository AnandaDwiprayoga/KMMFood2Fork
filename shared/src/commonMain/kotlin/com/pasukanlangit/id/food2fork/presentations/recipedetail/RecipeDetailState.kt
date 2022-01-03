package com.pasukanlangit.id.food2fork.presentations.recipedetail

import com.pasukanlangit.id.food2fork.domain.model.GenericMessageInfo
import com.pasukanlangit.id.food2fork.domain.model.Recipe
import com.pasukanlangit.id.food2fork.domain.util.Queue

data class RecipeDetailState(
    val isLoading: Boolean = false,
    val errorQueue: Queue<GenericMessageInfo> = Queue(mutableListOf()),
    val recipe: Recipe? = null
){
    constructor(): this(
        isLoading = false,
        errorQueue = Queue(mutableListOf()),
        recipe = null
    )
}
