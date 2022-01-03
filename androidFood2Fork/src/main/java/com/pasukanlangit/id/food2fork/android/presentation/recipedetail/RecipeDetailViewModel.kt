package com.pasukanlangit.id.food2fork.android.presentation.recipedetail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pasukanlangit.id.food2fork.domain.model.GenericMessageInfo
import com.pasukanlangit.id.food2fork.domain.model.UIComponentType
import com.pasukanlangit.id.food2fork.domain.util.GenericMessageInfoQueueUtil
import com.pasukanlangit.id.food2fork.domain.util.Queue
import com.pasukanlangit.id.food2fork.interactors.recipeDetail.GetRecipe
import com.pasukanlangit.id.food2fork.presentations.recipedetail.RecipeDetailEvents
import com.pasukanlangit.id.food2fork.presentations.recipedetail.RecipeDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@ExperimentalStdlibApi
@HiltViewModel
class RecipeDetailViewModel @Inject
constructor(
    savedStateHandle: SavedStateHandle,// <- get all data passing in screen
    private val getRecipeInteractor: GetRecipe
): ViewModel(){
    private val _recipe = mutableStateOf(RecipeDetailState())
    val recipe: State<RecipeDetailState> = _recipe

    init {
        savedStateHandle.get<Int>("recipeId")?.let { recipeId ->
            onTriggerEvents(RecipeDetailEvents.GetRecipe(recipeId))
        } ?: appendToMessageQueue(
            messageInfo = GenericMessageInfo.Builder()
                .id(UUID.randomUUID().toString())
                .title("Error")
                .uiComponentType(UIComponentType.Dialog)
                .description("Unhandled Error")
                .build()
        )
    }

    fun onTriggerEvents(events: RecipeDetailEvents){
        when(events){
            is RecipeDetailEvents.GetRecipe -> {
                getRecipe(events.recipeId)
            }
            is RecipeDetailEvents.removeHeadMessageQueue -> {
                removeHeadQueue()
            }
        }
    }

    private fun removeHeadQueue() {
        try {
            val currentQueueMessage = recipe.value.errorQueue
            currentQueueMessage.remove()
            _recipe.value = recipe.value.copy(errorQueue = Queue(mutableListOf())) //force recompose
            _recipe.value = recipe.value.copy(errorQueue = currentQueueMessage)
        }catch (e: Exception){

        }
    }

    private fun getRecipe(recipeId: Int) {
        getRecipeInteractor(
            idRecipe = recipeId
        ).collectCommon(coroutineScope = viewModelScope) { recipeFlow ->
            _recipe.value = recipe.value.copy(isLoading = recipeFlow.isLoading)

            recipeFlow.data?.let { recipeValue ->
                _recipe.value = recipe.value.copy(recipe = recipeValue)
            }

            recipeFlow.message?.let { message ->
                appendToMessageQueue(message)
            }

        }
    }

    private fun appendToMessageQueue(messageInfo: GenericMessageInfo) {
        if(!GenericMessageInfoQueueUtil().doesMessageAlreadyExistInQueue(
                messageInfo = messageInfo,
                queue = recipe.value.errorQueue
            )){
            val queue = recipe.value.errorQueue
            queue.add(messageInfo)
            _recipe.value = recipe.value.copy(errorQueue = queue)
        }
    }

}