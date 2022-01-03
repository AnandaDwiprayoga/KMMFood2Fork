//
//  RecipeCard.swift
//  iosFood2Fork
//
//  Created by Mac on 30/12/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared
import SDWebImageSwiftUI

struct RecipeCard: View {
    private let recipe: Recipe
    
    init(recipe: Recipe) {
        self.recipe = recipe
    }
    
    var body: some View {
        VStack(alignment: .leading){
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
                Text(recipe.title)
                    .font(AppFont.avenir(fontSize: 19))
                    .foregroundColor(Color.black)
                    .frame(alignment: .center)
                
                Spacer()
                
                Text(String(recipe.rating))
                    .font(AppFont.avenir())
                    .foregroundColor(Color.black)
                    .frame(alignment: .trailing)
            }
            .padding(EdgeInsets(top: 8, leading: 8, bottom: 12, trailing: 8))
        }
        .background(Color("BackgroundCard"))
        .cornerRadius(8)
        .shadow(radius: 5)
    }
}

