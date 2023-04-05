//
//  NoteDetailViewModel.swift
//  iosApp
//
//  Created by Dhruv Sharma on 04/04/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared

extension NoteDetailScreen{
    @MainActor class NoteDetailViewModel : ObservableObject{
        private var noteDatasource: NoteDataSource?
        
        private var noteId: Int64? = nil
        @Published var noteTitle = ""
        @Published var noteContent = ""
        @Published private(set) var noteColor = Note.Companion().getRandomColor()
        
        init(noteDatasource: NoteDataSource? = nil) {
            self.noteDatasource = noteDatasource
           
        }
        
        func loadNoteIfExists(id: Int64?){
            if id != nil{
                self.noteId = id
                noteDatasource?.getNoteById(id: id!, completionHandler: {  note,error in
                    self.noteTitle = note?.title ?? ""
                    self.noteContent = note?.content ?? ""
                    self.noteColor = note?.colorHex ?? Note.Companion().getRandomColor()
                })
            }
        }
        
        
        func saveNote(onSaved: @escaping () -> Void){
            noteDatasource?.insertNote(note: Note(id: noteId == nil ? nil : KotlinLong(value: noteId!), title: self.noteTitle, content: self.noteContent, colorHex:  self.noteColor, created: DateTimeUtil().now()), completionHandler: { error in
                onSaved()
            })
        }
    
        func setParamsAndLoads(noteDatasource : NoteDataSource,noteId: Int64?){
            self.noteDatasource = noteDatasource
            loadNoteIfExists(id: noteId)
        }
         
    }
}
