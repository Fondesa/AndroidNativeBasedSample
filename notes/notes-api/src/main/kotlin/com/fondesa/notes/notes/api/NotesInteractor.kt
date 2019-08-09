package com.fondesa.notes.notes.api

interface NotesInteractor {

    fun insertNote(draft: Draft)

    fun deleteNote(noteId: Int)

    fun updateNote(noteId: Int, draft: Draft)

    fun updateNewDraftTitle(title: String)

    fun updateNewDraftDescription(description: String)

    fun updateExistingDraftTitle(title: String)

    fun updateExistingDraftDescription(description: String)

    fun getAllNotes(): List<Note>

    fun getNewDraft(): Draft?

    fun getExistingDraft(noteId: Int): Draft?

    fun persistChanges()
}