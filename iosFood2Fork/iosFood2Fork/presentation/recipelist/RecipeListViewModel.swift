//
//  RecipeListViewModel.swift
//  iosFood2Fork
//
//  Created by Mac on 29/12/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared

class RecipeListViewModel: ObservableObject {
    let searchRecipes: SearchRecipes
    let foodCategoryUtil: FoodCategoryUtil
    
    //state
    @Published var state: RecipeListState = RecipeListState()
    
    @Published var showDialog: Bool = false
    
    init(
        searchRecipes: SearchRecipes,
        foodCategoryUtil: FoodCategoryUtil
    ){
        self.searchRecipes = searchRecipes
        self.foodCategoryUtil = foodCategoryUtil
        onTriggerEvent(stateEvent: RecipeListEvent.LoadRecipes())
    }
    
    func onTriggerEvent(stateEvent: RecipeListEvent){
        switch stateEvent {
        case is RecipeListEvent.LoadRecipes:
            loadRecipes()
        case is RecipeListEvent.SubmitSearch:
            submitSearch()
        case is RecipeListEvent.NextPage:
            nextPage()
        case is RecipeListEvent.OnQueryChange:
            onUpdateQuery(newQuery: (stateEvent as! RecipeListEvent.OnQueryChange).newQuery)
        case is RecipeListEvent.OnSelectCategory:
            onUpdateSelectedCategory(category: (stateEvent as! RecipeListEvent.OnSelectCategory).category)
        case is RecipeListEvent.removeHeadMessageQueue:
            removeHeadFromQueue()
        default:
            doNothing()
        }
    }
    
    func doNothing(){
        
    }
    
    
    private func submitSearch(){
        resetSearchState()
        loadRecipes()
    }
    
    private func resetSearchState(){
        guard let currentState = getCurrentState() else {return}
        var foodCategory = currentState.selectedCategory
        if(foodCategory?.value != currentState.query){
            foodCategory = nil
        }
        self.state = self.state.doCopy(
            isLoading: currentState.isLoading,
            page: 1,
            query: currentState.query,
            selectedCategory: foodCategory,
            bottomRecipe: currentState.bottomRecipe,
            errorQueue: currentState.errorQueue,
            recipes: []
        )
    }
    
    private func onUpdateSelectedCategory(category: FoodCategory){
        guard let currentState = getCurrentState() else {return}
        self.state = self.state.doCopy(
            isLoading: currentState.isLoading,
            page: currentState.page,
            query: category.value,
            selectedCategory: category,
            bottomRecipe: currentState.bottomRecipe,
            errorQueue: currentState.errorQueue,
            recipes: currentState.recipes
        )
        submitSearch()
    }
    
    private func onUpdateQuery(newQuery: String){
        updateState(query: newQuery)
    }
    
    private func getCurrentState() -> RecipeListState? {
        return self.state.copy() as? RecipeListState
    }
    
    private func loadRecipes(){
        guard let currentState = getCurrentState() else {return}
        searchRecipes.invoke(
            page: Int32(currentState.page),
            query: currentState.query
        ).collectCommon(
            coroutineScope: nil,
            callback: { dataState in
                guard let state = dataState else {return}
                let loading = state.isLoading
            
                self.updateState(isLoading: loading)
                
                if let data = state.data as? [Recipe]{
                    self.appendRecipes(data)
                }
                
                if let message = state.message {
                    self.handleMessageByUIComponentType(message)
                }
            }
        )
        
    }
    
    //underscore infront of parameter it means no required passing named argument
    private func appendRecipes(_ recipes: [Recipe]){
        guard let currentState = getCurrentState() else {return}
        var currentRecipes = currentState.recipes
        currentRecipes.append(contentsOf: recipes)
        self.state = self.state.doCopy(
            isLoading: currentState.isLoading,
            page: currentState.page,
            query: currentState.query,
            selectedCategory: currentState.selectedCategory,
            bottomRecipe: currentState.bottomRecipe,
            errorQueue: currentState.errorQueue,
            recipes: currentRecipes
        )
        self.onUpdateBottomRecipe(recipe: currentRecipes[currentRecipes.count - 1])
    }
    
    private func nextPage(){
        guard let currentState = getCurrentState() else {return}
        updateState(page: Int(currentState.page) + 1)
        loadRecipes()
    }
    private func onUpdateBottomRecipe(recipe: Recipe){
        updateState(bottomRecipe: recipe)
    }
    
    func shouldQueryNextPage(recipe: Recipe) -> Bool {
        guard let currentState = getCurrentState() else {return false}
        if(recipe.id == currentState.bottomRecipe?.id){
            if(Int32(RecipeCacheImpl.Companion().RECIPE_PAGINATION_PAGE_SIZE) * Int32(currentState.page) <= currentState.recipes.count){
                if(!currentState.isLoading){
                    return true
                }
            }
        }
        return false
    }
    
    private func appendToQueue(message: GenericMessageInfo){
        guard let currentState = getCurrentState() else {return}
        let queue = currentState.errorQueue
        let queueUtil = GenericMessageInfoQueueUtil()
        
        if !queueUtil.doesMessageAlreadyExistInQueue(queue: queue, messageInfo: message){
            queue.add(element: message)
            updateState(queue: queue)
        }
    }
    private func handleMessageByUIComponentType(_ message: GenericMessageInfo){
        switch message.uiComponentType {
        case UIComponentType.Dialog() :
            appendToQueue(message: message)
        case UIComponentType.None() :
            print("\(message.description)")
        default :
            doNothing()
        }
    }
    
    private func removeHeadFromQueue(){
        guard let currentState = getCurrentState() else {return}
        let queue = currentState.errorQueue
        do{
            try queue.remove()
            updateState(queue: queue)
        }catch {
            print("\(error)")
        }
    }
    /**
     *  Not everything can be conveniently updated with this function.
     *  Things like recipes and selectedCategory must have their own functions.
     *  Basically if more then one action must be taken then it cannot be updated with this function.
     *  ex: updating selected category requires us to 1) update category, 2) update the query, 3) trigger new search event
     */
    func updateState(
        isLoading: Bool? = nil,
        page: Int? = nil,
        query: String? = nil,
        bottomRecipe: Recipe? = nil,
        queue: Queue<GenericMessageInfo>? = nil
    ){
        let currentState = self.state.copy() as! RecipeListState
        self.state = self.state.doCopy(
            isLoading: isLoading ?? currentState.isLoading,
            page: Int32(page ?? Int(currentState.page)),
            query: query ?? currentState.query,
            selectedCategory: currentState.selectedCategory,
            bottomRecipe: bottomRecipe ?? currentState.bottomRecipe,
            errorQueue: queue ?? currentState.errorQueue,
            recipes: currentState.recipes
        )
        self.showDialog = currentState.errorQueue.count() > 0
    }
    
    
}
