package com.pasukanlangit.id.food2fork.android.presentation.recipelist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pasukanlangit.id.food2fork.domain.model.ButtonMessageAction
import com.pasukanlangit.id.food2fork.domain.model.GenericMessageInfo
import com.pasukanlangit.id.food2fork.domain.model.Recipe
import com.pasukanlangit.id.food2fork.domain.model.UIComponentType
import com.pasukanlangit.id.food2fork.domain.util.GenericMessageInfoQueueUtil
import com.pasukanlangit.id.food2fork.domain.util.Queue
import com.pasukanlangit.id.food2fork.interactors.recipeList.SearchRecipes
import com.pasukanlangit.id.food2fork.presentations.recipelist.FoodCategory
import com.pasukanlangit.id.food2fork.presentations.recipelist.RecipeListEvent
import com.pasukanlangit.id.food2fork.presentations.recipelist.RecipeListState
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class RecipeListViewModel
@Inject constructor(
    private val searchRecipes: SearchRecipes
): ViewModel(){

    private val _state = mutableStateOf(RecipeListState())
    val state: State<RecipeListState> = _state

    init {
        onTriggerEvent(RecipeListEvent.LoadRecipes)

        //TODO: this just for testing purpose
        appendToMessageQueue(
            GenericMessageInfo.Builder()
                .id(UUID.randomUUID().toString())
                .title("Error")
                .positive(
                    ButtonMessageAction(
                        btnLabel = "Ok Rice",
                        onBtnClicked = {
                            _state.value = state.value.copy(query = "Rice")
                            searchRecipesQuery()
                        }
                    )
                )
                .negative(
                    ButtonMessageAction(
                        btnLabel = "No ~Ice",
                        onBtnClicked = {
                            _state.value = state.value.copy(query = "Ice Cream")
                            searchRecipesQuery()
                        }
                    )
                )
                .uiComponentType(UIComponentType.Dialog)
                .description("Force error from viewmodel")
                .build()
        )
    }

    fun onTriggerEvent(event: RecipeListEvent){
        when(event){
            is RecipeListEvent.LoadRecipes -> {
                loadSearchRecipes()
            }
            is RecipeListEvent.NextPage -> {
                loadNextRecipes()
            }
            is RecipeListEvent.OnQueryChange -> {
                changeQuery(event.newQuery)
            }
            is RecipeListEvent.SubmitSearch -> {
                searchRecipesQuery()
            }
            is RecipeListEvent.OnSelectCategory -> {
                selectCategory(event.category)
            }
            is RecipeListEvent.removeHeadMessageQueue -> {
                removeHeadQueue()
            }
            else -> {
                appendToMessageQueue(
                    messageInfo = GenericMessageInfo.Builder()
                        .id(UUID.randomUUID().toString())
                        .title("Error")
                        .uiComponentType(UIComponentType.Dialog)
                        .description("Unhandled Error")
                        .build()
                )
            }
        }
    }

    private fun appendToMessageQueue(messageInfo: GenericMessageInfo) {
        if(!GenericMessageInfoQueueUtil().doesMessageAlreadyExistInQueue(
            messageInfo = messageInfo,
            queue = state.value.errorQueue
        )){
            val queue = state.value.errorQueue
            queue.add(messageInfo)
            _state.value = state.value.copy(errorQueue = queue)
        }
    }

    private fun removeHeadQueue() {
        try {
            val currentQueueMessage = state.value.errorQueue
            currentQueueMessage.remove()
            _state.value = state.value.copy(errorQueue = Queue(mutableListOf())) //force recompose
            _state.value = state.value.copy(errorQueue = currentQueueMessage)
        }catch (e: Exception){

        }
    }

    private fun selectCategory(category: FoodCategory) {
        _state.value = state.value.copy(selectedCategory = category, query = category.value)
        searchRecipesQuery()
    }

    private fun searchRecipesQuery() {
        _state.value = state.value.copy(recipes = listOf(), page = 1)
        loadSearchRecipes()
    }

    private fun changeQuery(newQuery: String) {
        _state.value = state.value.copy(query = newQuery, selectedCategory = null)
    }


    private fun loadNextRecipes() {
        val currentPage = state.value.page
        _state.value = state.value.copy(page = currentPage+1)
        loadSearchRecipes()
    }

    private fun loadSearchRecipes() {
        searchRecipes(
            page = state.value.page,
            query = state.value.query
        ).collectCommon(coroutineScope = viewModelScope) { dataState ->
            _state.value = state.value.copy(isLoading = dataState.isLoading)

            dataState.data?.let { recipes ->
                appendRecipes(recipes)
            }

            dataState.message?.let { message ->
                appendToMessageQueue(
                    messageInfo = message
                )
            }
        }
    }


    private fun appendRecipes(recipes: List<Recipe>){
        val currentRecipes = ArrayList(state.value.recipes)
        currentRecipes.addAll(recipes)
        _state.value = state.value.copy(recipes = currentRecipes, isLoading = false)
    }
}