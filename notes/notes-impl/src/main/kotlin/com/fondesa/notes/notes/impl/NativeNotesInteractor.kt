package com.fondesa.notes.notes.impl

import com.fondesa.notes.notes.api.DraftNote
import com.fondesa.notes.notes.api.Note
import com.fondesa.notes.notes.api.NotesInteractor
import dagger.Reusable
import javax.inject.Inject

@Reusable
class NativeNotesInteractor @Inject constructor() : NotesInteractor {

    private val handle: Long by lazy { getRepositoryHandle() }

    override fun deleteNote(noteId: Int) = deleteNote(handle, noteId)

    override fun insertNote(draftNote: DraftNote) =
        insertNote(handle, draftNote)

    override fun updateNote(noteId: Int, draftNote: DraftNote) =
        updateNote(handle, noteId, draftNote)

    override fun getAllNotes(): List<Note> = getAllNotes(handle).toList()

    private external fun getRepositoryHandle(): Long

    private external fun deleteNote(handle: Long, noteId: Int)

    private external fun insertNote(handle: Long, draftNote: DraftNote)

    private external fun updateNote(handle: Long, noteId: Int, draftNote: DraftNote)

    private external fun getAllNotes(handle: Long): Array<Note>
}