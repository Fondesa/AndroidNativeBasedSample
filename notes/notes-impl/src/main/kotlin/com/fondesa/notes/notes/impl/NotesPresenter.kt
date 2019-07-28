package com.fondesa.notes.notes.impl

import com.fondesa.notes.notes.api.NotesRepository
import com.fondesa.notes.ui.api.qualifiers.ScreenScope
import javax.inject.Inject

@ScreenScope
class NotesPresenter @Inject constructor(
    private val view: NotesContract.View,
    private val notesRepository: NotesRepository
) : NotesContract.Presenter {

    override fun attach() {
        view.hideListContainer()
        view.hideZeroElementsView()
        val notes = notesRepository.getAll()
        if (notes.isEmpty()) {
            view.showZeroElementsView()
        } else {
            view.showListContainer()
            view.showNoteList(notes)
        }
    }

    override fun addButtonClicked() {
        view.showInsertNoteScreen()
    }

    override fun doneButtonClicked() {
        view.hideInsertNoteScreen()
    }
}