//
//  NoteListViewModel.swift
//  iosApp
//
//  Created by Dhruv Sharma on 04/04/23.
//  Copyright © 2023 Dhruv🐵. All rights reserved.
//

import Foundation
import shared

extension NoteListScreen{
    
    @MainActor class NoteListViewModel : ObservableObject{
        
        private let searchNotes = SearchNotes()
        
        private var notes = [Note]()
        @Published private(set) var filteredNotes = [Note]()
        
        @Published var searchText = " " {
            didSet{
                self.filteredNotes = searchNotes.execute(notes: self.notes,query: searchText)
            }
        }
        
        @Published private(set) var isSearchActive = false
 
        private var notedataSource: NoteDataSource? = nil
        
        init(notedataSource: NoteDataSource? = nil) {
            self.notedataSource = notedataSource
        }
        
        func setNoteDataSource(noteDataSource : NoteDataSource){
            self.notedataSource = noteDataSource
//            noteDataSource.insertNote(note: Note(id: nil, title: "First Note", content: "This is content of first note", colorHex: 0xff4434, created: DateTimeUtil().now())) { error in
//
//            }
        }
        
        
        func loadNotes(){
            notedataSource?.getAllNotes(completionHandler: { notes,error in
                self.notes = notes ?? []
                self.filteredNotes = self.notes
            })
        }
        
        func deleteNoteById(id: Int64?){
            if id != nil{
                notedataSource?.deleteNoteById(id: id!, completionHandler: { Error in
                    self.loadNotes()
                })
            }
        }
        
        func toggleIsSearchActive(){
            isSearchActive = !isSearchActive
            if !isSearchActive {  /// If not focus reset `searchText`
                searchText = ""
            }
            
        }
    }
}
