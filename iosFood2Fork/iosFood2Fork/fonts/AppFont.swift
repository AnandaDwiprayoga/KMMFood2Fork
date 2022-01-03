//
//  AppFont.swift
//  iosFood2Fork
//
//  Created by Mac on 31/12/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI

struct AppFont {
    static func avenir(fontSize: CGFloat = 16) -> Font{
        return Font.custom("Avenir", size: fontSize)
    }
}
