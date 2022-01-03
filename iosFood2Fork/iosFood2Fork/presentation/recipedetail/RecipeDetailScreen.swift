//
//  RecipeDetailScreen.swift
//  iosFood2Fork
//
//  Created by Mac on 31/12/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared
import SDWebImageSwiftUI

struct RecipeDetailScreen: View {
//    private let cacheModule: CacheModule
//    private let getRecipeModule: GetRecipe
    private let cacheModule: CacheModule
    private let getRecipeModule: GetRecipeModule
    private let recipeId: Int
    private let dateUtil: DateTimeUtil = DateTimeUtil()
    
    @ObservedObject var viewModel: RecipeDetailViewModel
    
    init(
        recipeId: Int,
        cacheModule: CacheModule
    ) {
        self.recipeId = recipeId
        self.cacheModule = cacheModule
        self.getRecipeModule = GetRecipeModule(
            cacheModule: self.cacheModule
        )
        self.viewModel = RecipeDetailViewModel(recipeId: recipeId, getRecipe: getRecipeModule.getRecipe)
    }
    var body: some View {
        ZStack {
            if viewModel.state.isLoading {
                ProgressView("Load Recipe...")
                    .font(AppFont.avenir())
            }
            if let recipe = viewModel.state.recipe {
                ScrollView(.vertical){
                    VStack(alignment: .leading) {
                        //Note: order matter in swift ui
                        WebImage(url: URL(string: recipe.featuredImage))
                            .resizable() // order 1.
                            .placeholder(Image(systemName: "photo"))
                            .placeholder{
                                Rectangle().foregroundColor(.white)
                            }
                            .indicator(.activity)
                            .transition(.fade(duration: 0.5))
                            .scaledToFill() // order 2.
                            .frame(height: 250, alignment: .center) // order 3.
                            .clipped() // order 4.
                        
                        HStack(alignment: .lastTextBaseline){
                            Text("Updated \(dateUtil.humanizeDatetime(date: recipe.dateUpdated)) by \(recipe.publisher)")
                                .font(AppFont.avenir())
                                .foregroundColor(Color.gray)
                                .frame(alignment: .center)
                            
                            Spacer()
                            
                            Text(String(recipe.rating))
                                .font(AppFont.avenir())
                                .foregroundColor(Color.black)
                                .frame(alignment: .trailing)
                        }
                        .padding(EdgeInsets(top: 0, leading: 8, bottom: 8, trailing: 8))
                        
                        ForEach(recipe.ingredients as [String], id: \.self){ ingridient in
                            Text(ingridient)
                                .foregroundColor(Color.black)
                                .padding(.horizontal, 8)
                            Spacer(minLength: 4)
                        }
                    }
                }
                .navigationBarTitle(Text(recipe.title), displayMode: .inline)
                .background(Color.white)
            }else{
                Text("Unable to load detail recipe")
            }
        }
    }
}

//struct RecipeDetailScreen_Previews: PreviewProvider {
//    static var previews: some View {
//        RecipeDetailScreen()
//    }
//}
