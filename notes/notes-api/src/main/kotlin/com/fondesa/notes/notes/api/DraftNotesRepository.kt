package com.fondesa.notes.notes.api

interface DraftNotesRepository {

    fun beginCreationSession()

    fun beginUpdateSession(note: Note)

    fun endSession()

    fun updateTitle(title: String)

    fun updateDescription(description: String)

    fun getDraftCreationNote(): DraftNote?

    fun getDraftUpdateNote(noteId: Int): DraftNote?
}