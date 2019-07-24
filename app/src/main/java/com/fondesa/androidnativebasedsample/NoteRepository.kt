package com.fondesa.androidnativebasedsample

class NoteRepository(dbPath: String) {

    private val repositoryHandle = initialize(dbPath)

    fun remove(noteId: Int) = remove(repositoryHandle, noteId)

    fun insert(draftNote: DraftNote) = insert(repositoryHandle, draftNote)

    fun update(noteId: Int, draftNote: DraftNote) = update(repositoryHandle, noteId, draftNote)

    fun getAll(): List<Note> = getAll(repositoryHandle).toList()

    private external fun initialize(dbPath: String): Long

    private external fun remove(handle: Long, noteId: Int)

    private external fun insert(handle: Long, draftNote: DraftNote)

    private external fun update(handle: Long, noteId: Int, draftNote: DraftNote)

    private external fun getAll(handle: Long): Array<Note>

    companion object {
        init {
            System.loadLibrary("jni-wrapper")
        }
    }
}