//
//  GenericMessageInfoAlert.swift
//  iosFood2Fork
//
//  Created by Mac on 31/12/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared

struct GenericMessageInfoAlert {
    func build(
        message: GenericMessageInfo,
        onRemoveHeadMessage: @escaping () -> Void
    ) -> Alert {
        return Alert(
            title: Text(message.title).font(AppFont.avenir(fontSize: 18)),
            message: Text(message.title).font(AppFont.avenir()),
            primaryButton: .default(
                Text(message.positiveAction?.btnLabel ?? "Ok"),
                action: {
                    if(message.positiveAction != nil){
                        message.positiveAction!.onBtnClicked()
                    }
                    onRemoveHeadMessage()
                }
            ),
            secondaryButton: .default(
                Text(message.negativeAction?.btnLabel ?? "Cancel"),
                action: {
                    if(message.negativeAction != nil){
                        message.negativeAction!.onBtnClicked()
                    }
                    onRemoveHeadMessage()
                }
            )
        
        )
    }
}
