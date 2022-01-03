//
//  RecipeListScreen.swift
//  iosFood2Fork
//
//  Created by Mac on 29/12/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared

struct RecipeListScreen: View {
    
    private let networkModule: NetworkModule
    private let cacheModule: CacheModule
    private let searchRecipesModule: SearchRecipesModule
    private let foodCategoryUtil = FoodCategoryUtil()
    
    @ObservedObject var viewModel: RecipeListViewModel
    
    
    init(
        networkModule: NetworkModule,
        cacheModule: CacheModule
    ){
        self.networkModule = networkModule
        self.cacheModule = cacheModule
        self.searchRecipesModule = SearchRecipesModule(
            networkModule: networkModule, cacheModule: cacheModule
        )
        self.viewModel = RecipeListViewModel(
            searchRecipes: searchRecipesModule.searchRecipes,
            foodCategoryUtil: foodCategoryUtil
        )
    }
    var body: some View {
        NavigationView {
            ZStack{
                VStack {
                    SearchAppBar(
                        query: viewModel.state.query,
                        categories: foodCategoryUtil.getAllFoodCategories(),
                        selectedCategory: viewModel.state.selectedCategory,
                        onTriggerEvent: viewModel.onTriggerEvent
                    )
                    List {
                        ForEach(viewModel.state.recipes, id: \.self.id){ recipe in
                            ZStack {
                                VStack {
                                    RecipeCard(recipe: recipe)
                                        .onAppear(perform: {
                                            if viewModel.shouldQueryNextPage(recipe: recipe){
                                                viewModel.onTriggerEvent(stateEvent: RecipeListEvent.NextPage())
                                            }
                                        })
                                    
                                    NavigationLink(
                                        destination: RecipeDetailScreen(
                                            recipeId: Int(recipe.id),
                                            cacheModule: cacheModule
                                        )
                                    ){
                                        EmptyView()
                                    }.hidden().frame(width: 0)
                                }
                            }
                            .listRowInsets(EdgeInsets())                            
                        }
                    }
                    .listStyle(PlainListStyle())
                    .background(Color.gray)
                }
                if viewModel.state.isLoading {
                    ProgressView("Searching Recipes...")
                }
            }
            .navigationBarHidden(true)
            .alert(isPresented: $viewModel.showDialog, content: {
                let first = viewModel.state.errorQueue.peek()!
                return GenericMessageInfoAlert().build(message: first, onRemoveHeadMessage: {
                    viewModel.onTriggerEvent(stateEvent: RecipeListEvent.removeHeadMessageQueue())
                })
            })
        }
    }
}

//struct RecipeListScreen_Previews: PreviewProvider {
//    static var previews: some View {
//        RecipeListScreen()
//    }
//}
