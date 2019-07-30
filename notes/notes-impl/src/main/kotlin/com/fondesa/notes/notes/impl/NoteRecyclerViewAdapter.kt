package com.fondesa.notes.notes.impl

import androidx.recyclerview.widget.RecyclerView
import com.fondesa.notes.notes.api.Note
import com.fondesa.notes.ui.api.view.InteractiveRecyclerViewAdapter

/**
 * Defines a [RecyclerView.Adapter] for holders of type [NoteRecyclerViewHolder] which
 * should show a list of [Note].
 */
abstract class NoteRecyclerViewAdapter :
    InteractiveRecyclerViewAdapter<NoteRecyclerViewHolder>() {

    /**
     * Updates the current item list with the new list of [Note].
     *
     * @param items the notes which should be shown.
     */
    abstract fun updateList(items: List<Note>)

    interface OnNoteClickListener {

        fun onNoteClicked(note: Note)
    }
}