//
//  SearchAppBar.swift
//  iosFood2Fork
//
//  Created by Mac on 30/12/21.
//  Copyright © 2021 orgName. All rights reserved.
//
import SwiftUI
import shared

struct SearchAppBar: View {
    @State var query: String
    
    private let onTriggerEvent: (RecipeListEvent) -> Void
    private let categories: [FoodCategory]
    private let selectedCategory: FoodCategory?
    
    init(
        query: String,
        categories: [FoodCategory],
        selectedCategory: FoodCategory?,
        onTriggerEvent: @escaping (RecipeListEvent) -> Void
    ){
        self.onTriggerEvent = onTriggerEvent
        self._query = State(initialValue: query)
        self.categories = categories
        self.selectedCategory =  selectedCategory
    }
    
    var body: some View {
        VStack {
            HStack {
                Image(systemName: "magnifyingglass")
                TextField(
                    "Search...",
                    text: $query,
                    onCommit: {
                        onTriggerEvent(RecipeListEvent.SubmitSearch())
                    }
                )
                .onChange(of: query, perform: {value in
                    onTriggerEvent(RecipeListEvent.OnQueryChange(
                        newQuery: value
                    ))
                })
                .font(AppFont.avenir())
            }
            .padding(.bottom, 8)
            ScrollView(.horizontal){
                HStack(spacing: 10){
                    ForEach(categories, id: \.self){ category in
                        FoodCategoryChip(category: category.value, isSelected: category == selectedCategory)
                            .onTapGesture {
                                query = category.value
                                onTriggerEvent(RecipeListEvent.OnSelectCategory(category: category))
                            }
                    }
                }
            }
        }
        .padding(EdgeInsets(top: 8, leading: 8, bottom: 0, trailing: 8))
        .background(Color.white.opacity(0))
    }
}
