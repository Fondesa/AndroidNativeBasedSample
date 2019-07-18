package com.fondesa.androidnativebasedsample

class NoteRepository {

    private val repositoryHandle = initialize()

    fun remove(noteId: Int) = remove(repositoryHandle, noteId)

    fun insert(draftNote: DraftNote) = insert(repositoryHandle, draftNote)

    fun getAll() = getAll(repositoryHandle)

    private external fun initialize(): Long

    private external fun remove(handle: Long, noteId: Int)

    private external fun insert(handle: Long, draftNote: DraftNote)

    private external fun getAll(handle: Long): Note

    companion object {
        init {
            System.loadLibrary("jni-wrapper")
        }
    }
}