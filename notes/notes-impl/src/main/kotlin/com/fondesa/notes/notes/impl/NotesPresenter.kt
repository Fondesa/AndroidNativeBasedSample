package com.fondesa.notes.notes.impl

import com.fondesa.notes.notes.api.DraftNote
import com.fondesa.notes.notes.api.NotesRepository
import com.fondesa.notes.ui.api.qualifiers.ScreenScope
import javax.inject.Inject

@ScreenScope
class NotesPresenter @Inject constructor(
    private val view: NotesContract.View,
    private val notesRepository: NotesRepository
) : NotesContract.Presenter {

    private val noteScreenContent = NoteScreenContent(
        title = "",
        description = ""
    )
    private var buttonState = NoteButtonState.UNDEFINED
        set(value) {
            view.renderButtonState(value)
            field = value
        }

    override fun attach() {
        buttonState = NoteButtonState.ADD
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

        val draftNote = noteScreenContent.toDraftNote()
        notesRepository.insert(draftNote)

        val notes = notesRepository.getAll()
        view.hideZeroElementsView()
        view.showListContainer()
        view.showNoteList(notes)

        view.showNoteScreenTitle("")
        view.showNoteScreenDescription("")
    }

    override fun cancelButtonClicked() {
        view.hideInsertNoteScreen()
    }

    override fun insertNoteScreenShown() {
        buttonState = NoteButtonState.CANCEL
    }

    override fun insertNoteScreenHidden() {
        buttonState = NoteButtonState.ADD
    }

    override fun noteScreenTitleChanged(title: String) {
        noteScreenContent.title = title
        buttonState = if (noteScreenContent.isValid) {
            NoteButtonState.DONE
        } else {
            NoteButtonState.CANCEL
        }
    }

    override fun noteScreenDescriptionChanged(description: String) {
        noteScreenContent.description = description
        buttonState = if (noteScreenContent.isValid) {
            NoteButtonState.DONE
        } else {
            NoteButtonState.CANCEL
        }
    }

    private data class NoteScreenContent(
        var title: String,
        var description: String
    ) {

        val isValid: Boolean get() = title.isNotBlank() || description.isNotBlank()

        fun toDraftNote(): DraftNote = DraftNote(title, description)
    }
}