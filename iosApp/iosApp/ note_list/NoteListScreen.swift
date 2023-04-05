//
//  NoteListScreen.swift
//  iosApp
//
//  Created by Dhruv Sharma on 04/04/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct NoteListScreen: View {
    
    private var noteDataSource : NoteDataSource
    @StateObject var viewModel = NoteListViewModel(notedataSource: nil) // passing null
    
    @State private var isNoteSlected = false
    @State private var selectedNoteId : Int64? = nil
    
    init(noteDataSource: NoteDataSource) {
        self.noteDataSource = noteDataSource
    } // making datasource instance
    
    
    var body: some View {
        VStack{
            ZStack{
                NavigationLink(destination: NoteDetailScreen(noteDataSource: noteDataSource,noteId:selectedNoteId),isActive: $isNoteSlected) {
                    EmptyView()
                }.hidden()
                HideableSeachTextField<NoteDetailScreen>(onSearchToggled: {
                    viewModel.toggleIsSearchActive()
                }, destinationProvider: {
                    NoteDetailScreen(
                        noteDataSource: noteDataSource,
                        noteId: selectedNoteId
                    )
                }, isSearchActive: viewModel.isSearchActive,
                searchText: $viewModel.searchText
                ).frame(maxWidth: .infinity,minHeight: 40)
                .padding()
                if !viewModel.isSearchActive{
                    Text("All Notes")
                        .font(.title2)
                }
            }
            List{
                ForEach(viewModel.filteredNotes,id: \.self.id){ note in
                    Button(action: {
                        isNoteSlected = true
                        selectedNoteId = note.id?.int64Value
                    }){
                        NoteItem(note: note, onDeleteClicked: {
                            viewModel.deleteNoteById(id: note.id?.int64Value)
                        })
                    }
                }
            }.onAppear{
                viewModel.loadNotes()
            } .listStyle(.plain)
            .listRowSeparator(.hidden)
            
        }
        .onAppear{
            viewModel.setNoteDataSource(noteDataSource: noteDataSource)
        }
    }
}

struct NoteListScreen_Previews: PreviewProvider {
    static var previews: some View {
       EmptyView()
    }
}
