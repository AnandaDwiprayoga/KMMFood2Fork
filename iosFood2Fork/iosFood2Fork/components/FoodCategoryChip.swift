//
//  FoodCategoryChip.swift
//  iosFood2Fork
//
//  Created by Mac on 30/12/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI

struct FoodCategoryChip: View {
    
    private let category: String
    private let isSelected: Bool
    
    init(
        category: String,
        isSelected: Bool
    ) {
        self.category = category
        self.isSelected = isSelected
    }
    
    var body: some View {
        HStack {
            Text(category)
                .padding(8)
                .font(AppFont.avenir())
                .background(isSelected ? Color.gray : Color.blue)
                .foregroundColor(Color.white)
        }
        .cornerRadius(10)
    }
}
//
//struct FoodCategoryChip_Previews: PreviewProvider {
//    static var previews: some View {
//        FoodCategoryChip()
//    }
//}
