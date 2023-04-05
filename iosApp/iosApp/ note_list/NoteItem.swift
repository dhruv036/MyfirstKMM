//
//  NoteItem.swift
//  iosApp
//
//  Created by Dhruv Sharma on 04/04/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct NoteItem: View {
    
    var note:Note
    var onDeleteClicked : () -> Void
    
    
    var body: some View {
        VStack(alignment: .leading){
            HStack{
                Text(note.title)
                    .font(.title3)
                    .fontWeight(.semibold)
                Spacer()
                Button(action: onDeleteClicked){
                    Image(systemName:"xmark").foregroundColor(.black)
                    
                }
            }.padding(.bottom, 3)
            Text(note.content).fontWeight(.light).padding(.bottom,3)
            HStack{
                Spacer()
                Text(DateTimeUtil().formateNoteDate(dateTime: note.created)).font(.footnote).fontWeight(.light)
            }
         
        }.padding()
        .background(Color(hex:note.colorHex))
        .clipShape(RoundedRectangle(cornerRadius: 5.0))
    }
}

struct NoteItem_Previews: PreviewProvider {
    static var previews: some View {
        NoteItem(
            note: Note(id: 101, title: "My note", content: "Hello World", colorHex:  0xff3334, created: DateTimeUtil().now()),
            onDeleteClicked: {}
        )
    }
}
