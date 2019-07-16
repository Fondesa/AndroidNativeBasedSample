package com.fondesa.androidnativebasedsample

class NoteRepository {

    private val repositoryHandle = initialize()

    fun remove(noteId: Int) = remove(repositoryHandle, noteId)

    private external fun initialize(): Long

    private external fun remove(handle: Long, noteId: Int)

    companion object {
        init {
            System.loadLibrary("jni-wrapper")
        }
    }
}