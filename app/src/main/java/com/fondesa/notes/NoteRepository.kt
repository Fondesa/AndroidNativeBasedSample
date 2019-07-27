package com.fondesa.notes

class NoteRepository {

    private val repositoryHandle = getRepositoryHandle()

    fun remove(noteId: Int) = remove(repositoryHandle, noteId)

    fun insert(draftNote: DraftNote) = insert(repositoryHandle, draftNote)

    fun update(noteId: Int, draftNote: DraftNote) = update(repositoryHandle, noteId, draftNote)

    fun getAll(): List<Note> = getAll(repositoryHandle).toList()

    private external fun getRepositoryHandle(): Long

    private external fun remove(handle: Long, noteId: Int)

    private external fun insert(handle: Long, draftNote: DraftNote)

    private external fun update(handle: Long, noteId: Int, draftNote: DraftNote)

    private external fun getAll(handle: Long): Array<Note>
}