package com.fondesa.notes.notes.impl

import com.fondesa.notes.notes.api.DraftNote
import com.fondesa.notes.notes.api.DraftNotesRepository
import com.fondesa.notes.notes.api.Note
import dagger.Reusable
import javax.inject.Inject

@Reusable
class NativeDraftNotesRepository @Inject constructor() : DraftNotesRepository {

    private val handle: Long by lazy { getRepositoryHandle() }

    override fun beginCreationSession() = beginCreationSession(handle)

    override fun beginUpdateSession(note: Note) = beginUpdateSession(handle, note)

    override fun endSession() = endSession(handle)

    override fun updateTitle(title: String) = updateTitle(handle, title)

    override fun updateDescription(description: String) = updateDescription(handle, description)

    override fun getDraftCreationNote(): DraftNote? = getDraftCreationNote(handle)

    override fun getDraftUpdateNote(noteId: Int): DraftNote? = getDraftUpdateNote(handle, noteId)

    private external fun getRepositoryHandle(): Long

    private external fun beginCreationSession(handle: Long)

    private external fun beginUpdateSession(handle: Long, note: Note)

    private external fun endSession(handle: Long)

    private external fun updateTitle(handle: Long, title: String)

    private external fun updateDescription(handle: Long, description: String)

    private external fun getDraftCreationNote(handle: Long): DraftNote?

    private external fun getDraftUpdateNote(handle: Long, noteId: Int): DraftNote?
}