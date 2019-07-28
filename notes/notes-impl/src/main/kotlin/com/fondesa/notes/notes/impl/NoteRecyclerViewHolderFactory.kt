package com.fondesa.notes.notes.impl

import android.view.ViewGroup

/**
 * Creates a new [NoteRecyclerViewHolder] which inflates its view in the given parent.
 */
interface NoteRecyclerViewHolderFactory {

    /**
     * Creates a [NoteRecyclerViewHolder].
     *
     * @param parent the view in which the holder's view should be inflated.
     * @return a new instance of [NoteRecyclerViewHolder].
     */
    fun create(parent: ViewGroup): NoteRecyclerViewHolder
}