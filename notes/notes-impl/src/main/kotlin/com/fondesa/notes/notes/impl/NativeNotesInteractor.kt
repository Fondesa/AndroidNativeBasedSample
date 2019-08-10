package com.fondesa.notes.notes.impl

import com.fondesa.notes.notes.api.Draft
import com.fondesa.notes.notes.api.Note
import com.fondesa.notes.notes.api.NotesInteractor
import dagger.Reusable
import javax.inject.Inject

@Reusable
class NativeNotesInteractor @Inject constructor() : NotesInteractor {

    private val handle: Long by lazy { getRepositoryHandle() }

    override fun insertNote(draft: Draft) =
        insertNote(handle, draft)

    override fun deleteNote(noteId: Int) = deleteNote(handle, noteId)

    override fun updateNote(noteId: Int, draft: Draft) =
        updateNote(handle, noteId, draft)

    override fun updateNewDraftTitle(title: String) = updateNewDraftTitle(handle, title)

    override fun updateNewDraftDescription(description: String) =
        updateNewDraftDescription(handle, description)

    override fun updateExistingDraftTitle(noteId: Int, title: String) =
        updateExistingDraftTitle(handle, noteId, title)

    override fun updateExistingDraftDescription(noteId: Int, description: String) =
        updateExistingDraftDescription(handle, noteId, description)

    override fun getAllNotes(): List<Note> = getAllNotes(handle).toList()

    override fun getNewDraft(): Draft? = getNewDraft(handle)

    override fun getExistingDraft(noteId: Int): Draft? = getExistingDraft(handle, noteId)

    override fun persistChanges() = persistChanges(handle)

    private external fun getRepositoryHandle(): Long

    private external fun insertNote(handle: Long, draft: Draft)

    private external fun deleteNote(handle: Long, noteId: Int)

    private external fun updateNote(handle: Long, noteId: Int, draft: Draft)

    private external fun updateNewDraftTitle(handle: Long, title: String)

    private external fun updateNewDraftDescription(handle: Long, description: String)

    private external fun updateExistingDraftTitle(handle: Long, noteId: Int, title: String)

    private external fun updateExistingDraftDescription(
        handle: Long,
        noteId: Int,
        description: String
    )

    private external fun getAllNotes(handle: Long): Array<Note>

    private external fun getNewDraft(handle: Long): Draft?

    private external fun getExistingDraft(handle: Long, noteId: Int): Draft?

    private external fun persistChanges(handle: Long)

}