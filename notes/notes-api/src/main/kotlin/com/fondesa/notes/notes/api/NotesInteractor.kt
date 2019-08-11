package com.fondesa.notes.notes.api

interface NotesInteractor {

    fun insertNote(draft: Draft)

    fun updateNote(noteId: Int, draft: Draft)

    fun updateNewDraftTitle(title: String)

    fun updateNewDraftDescription(description: String)

    fun updateExistingDraftTitle(noteId: Int, title: String)

    fun updateExistingDraftDescription(noteId: Int, description: String)

    fun getAllNotes(): List<Note>

    fun getNotesByText(text: String): List<Note>

    fun getNewDraft(): Draft?

    fun getExistingDraft(noteId: Int): Draft?

    fun deleteNote(noteId: Int)

    fun deleteNewDraft()

    fun deleteExistingDraft(noteId: Int)

    fun persistChanges()
}