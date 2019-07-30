package com.fondesa.notes.notes.impl

import android.view.ViewGroup
import com.fondesa.notes.notes.api.Note
import com.fondesa.notes.ui.api.util.inflateChild
import com.fondesa.notes.ui.api.view.RecyclerViewInteraction
import com.fondesa.notes.ui.api.view.RecyclerViewRowGesture
import com.google.auto.factory.AutoFactory
import kotlinx.android.synthetic.main.row_note.*

/**
 * Implementation of [NoteRecyclerViewHolder] which shows a [Note].
 * It will generate its factory automatically.
 *
 * @param parent the parent of the root view handled by this holder.
 */
@AutoFactory(implementing = [NoteRecyclerViewHolderFactory::class])
class NoteRecyclerViewHolderImpl(parent: ViewGroup) :
    NoteRecyclerViewHolder(parent.inflateChild(R.layout.row_note)) {

    override val interactions: Array<RecyclerViewInteraction> =
        arrayOf(RecyclerViewInteraction(itemView, RecyclerViewRowGesture.CLICK))

    override fun bind(item: Note) {
        titleTextView.text = item.title
        descriptionTextView.text = item.description
    }
}