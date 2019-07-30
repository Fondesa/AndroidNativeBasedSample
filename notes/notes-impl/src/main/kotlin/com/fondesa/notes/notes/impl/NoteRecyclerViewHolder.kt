package com.fondesa.notes.notes.impl

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.fondesa.notes.notes.api.Note
import com.fondesa.notes.ui.api.view.InteractiveRecyclerViewHolder

/**
 * Defines a [RecyclerView.ViewHolder] which should show a [Note].
 *
 * @param itemView the root view handled by this holder.
 */
abstract class NoteRecyclerViewHolder(itemView: View) : InteractiveRecyclerViewHolder(itemView) {

    /**
     * Binds a [Note] to this view to show its information.
     *
     * @param item the [Note] which should be bound.
     */
    abstract fun bind(item: Note)
}