package com.fondesa.notes.notes.api

interface NotesRepository {

    fun remove(noteId: Int)

    fun insert(draftNote: DraftNote)

    fun update(noteId: Int, draftNote: DraftNote)

    fun getAll(): List<Note>
}