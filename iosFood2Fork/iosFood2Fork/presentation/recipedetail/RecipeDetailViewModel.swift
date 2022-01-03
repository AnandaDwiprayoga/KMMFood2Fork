//
//  RecipeDetailViewModel.swift
//  iosFood2Fork
//
//  Created by Mac on 31/12/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared

class RecipeDetailViewModel: ObservableObject {
    private let getRecipe: GetRecipe
    
    @Published var state: RecipeDetailState = RecipeDetailState()
    
    init(
        recipeId: Int,
        getRecipe: GetRecipe
    ){
        self.getRecipe = getRecipe
        onTriggerEvent(stateEvent: RecipeDetailEvents.GetRecipe(recipeId: Int32(recipeId)))
    }
    
    func onTriggerEvent(stateEvent: RecipeDetailEvents){
        switch stateEvent {
        case is RecipeDetailEvents.GetRecipe :
            getRecipeDetail(recipeId: (stateEvent as! RecipeDetailEvents.GetRecipe).recipeId)
        case is RecipeDetailEvents.removeHeadMessageQueue :
            doNothing()
        default:
            doNothing()
        }
    }
    
    private func getRecipeDetail(recipeId: Int32){
        getRecipe.invoke(idRecipe: recipeId)
            .collectCommon(coroutineScope: nil, callback: { dataState in
                if let data = dataState?.data {
                    self.updateState(recipe: data)
                }
                
                if let message = dataState?.message {
                    self.handleMessageByUIComponentType(message)
                }
                
                let loading = dataState?.isLoading ?? false
                self.updateState(isLoading: loading)
            })
    }
    
    private func handleMessageByUIComponentType(_ message: GenericMessageInfo){
        
    }
    
    
    private func updateState(
        isLoading: Bool? = nil,
        recipe: Recipe? = nil,
        queue: Queue<GenericMessageInfo>? = nil
    ){
        guard let currentState = getCurrentState() else {return}
        self.state = self.state.doCopy(
            isLoading: isLoading ?? currentState.isLoading,
            errorQueue: queue ?? currentState.errorQueue,
            recipe: recipe ?? currentState.recipe
        )
    }
    
    private func doNothing(){
        
    }
    
    private func getCurrentState() -> RecipeDetailState? {
        return self.state.copy() as? RecipeDetailState
    }
}
