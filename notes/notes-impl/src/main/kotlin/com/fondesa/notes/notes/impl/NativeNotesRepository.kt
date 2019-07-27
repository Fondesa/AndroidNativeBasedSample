package com.fondesa.notes.notes.impl

import com.fondesa.notes.notes.api.DraftNote
import com.fondesa.notes.notes.api.Note
import com.fondesa.notes.notes.api.NotesRepository
import dagger.Reusable
import javax.inject.Inject

@Reusable
class NativeNotesRepository @Inject constructor() : NotesRepository {

    private val handle: Long by lazy { getRepositoryHandle() }

    override fun remove(noteId: Int) = remove(handle, noteId)

    override fun insert(draftNote: DraftNote) = insert(handle, draftNote)

    override fun update(noteId: Int, draftNote: DraftNote) =
        update(handle, noteId, draftNote)

    override fun getAll(): List<Note> = getAll(handle).toList()

    private external fun getRepositoryHandle(): Long

    private external fun remove(handle: Long, noteId: Int)

    private external fun insert(handle: Long, draftNote: DraftNote)

    private external fun update(handle: Long, noteId: Int, draftNote: DraftNote)

    private external fun getAll(handle: Long): Array<Note>
}