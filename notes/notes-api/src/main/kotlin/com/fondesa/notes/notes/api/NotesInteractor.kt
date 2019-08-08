package com.fondesa.notes.notes.api

interface NotesInteractor {

    fun insertNote(draftNote: DraftNote)

    fun deleteNote(noteId: Int)

    fun updateNote(noteId: Int, draftNote: DraftNote)

    fun updateNewDraftTitle(title: String)

    fun updateNewDraftDescription(description: String)

    fun updateExistingDraftTitle(title: String)

    fun updateExistingDraftDescription(description: String)

    fun getAllNotes(): List<Note>

    fun getNewDraft(): DraftNote?

    fun getExistingDraft(noteId: Int): DraftNote?

    fun persistChanges()
}