package com.fondesa.notes.notes.impl

import android.view.View
import android.view.ViewGroup
import com.fondesa.notes.notes.api.Note
import javax.inject.Inject

/**
 * Implementation of [NoteRecyclerViewAdapter] which uses a [NoteRecyclerViewHolderFactory]
 * to create the view holder.
 */
class NoteRecyclerViewAdapterImpl @Inject constructor(
    private val holderFactory: NoteRecyclerViewHolderFactory,
    private val clickListener: OnNoteClickListener
) : NoteRecyclerViewAdapter() {

    private val items = mutableListOf<Note>()

    init {
        // The ids are stable to optimize the performances.
        setHasStableIds(true)
    }

    override fun getItemCount(): Int = items.size

    override fun getItemId(position: Int): Long {
        val item = items[position]
        // The id of the item is unique
        return item.id.toLong()
    }

    override fun onCreateInteractiveViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NoteRecyclerViewHolder = holderFactory.create(parent)

    override fun onBindViewHolder(holder: NoteRecyclerViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun updateList(items: List<Note>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCellClick(view: View, position: Int) {
        super.onCellClick(view, position)
        val note = items[position]
        clickListener.onNoteClicked(note)
    }
}