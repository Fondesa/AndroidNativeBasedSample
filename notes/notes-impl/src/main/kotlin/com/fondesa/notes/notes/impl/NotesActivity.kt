package com.fondesa.notes.notes.impl

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import com.fondesa.notes.notes.api.DraftNote
import com.fondesa.notes.notes.api.Note
import com.fondesa.notes.ui.api.view.BottomSheetVisibleCallback
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_notes.*
import kotlinx.android.synthetic.main.sheet_insert_note.*
import javax.inject.Inject

class NotesActivity : AppCompatActivity(),
    NotesContract.View,
    BottomSheetVisibleCallback.Listener {

    @Inject
    internal lateinit var presenter: NotesContract.Presenter

    @Inject
    internal lateinit var adapter: NoteRecyclerViewAdapter

    private val noteSheet by lazy { BottomSheetBehavior.from(insertNoteContainer) }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        noteSheet.state = BottomSheetBehavior.STATE_HIDDEN
        noteSheet.setBottomSheetCallback(BottomSheetVisibleCallback(this))

        noteActionButton.setOnAddClickListener(presenter::addButtonClicked)
        noteActionButton.setOnDoneClickListener(presenter::doneButtonClicked)
        noteActionButton.setOnCancelClickListener(presenter::cancelButtonClicked)

        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        // Set the adapter on the RecyclerView.
        recyclerView.adapter = adapter

        val repository = NativeNotesRepository()
        repository.insert(
            DraftNote(
                "first-title",
                "first-description"
            )
        )
        repository.insert(
            DraftNote(
                "second-title",
                "second-description"
            )
        )
        val notes = repository.getAll()
        repository.update(
            notes[1].id,
            DraftNote(
                "updated-second-title",
                "updated-second-description"
            )
        )
        val notesAfterUpdate = repository.getAll()
        repository.remove(notes[0].id)
        val notesAfterRemove = repository.getAll()

        presenter.attach()
    }

    override fun showListContainer() {
        recyclerView.visibility = View.VISIBLE
    }

    override fun hideListContainer() {
        recyclerView.visibility = View.INVISIBLE
    }

    override fun showZeroElementsView() {
        zeroElementsTextView.visibility = View.VISIBLE
    }

    override fun hideZeroElementsView() {
        zeroElementsTextView.visibility = View.INVISIBLE
    }

    override fun showNoteList(noteList: List<Note>) {
        adapter.updateList(noteList)
    }

    override fun showInsertNoteScreen() {
        noteSheet.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    override fun hideInsertNoteScreen() {
        noteSheet.state = BottomSheetBehavior.STATE_HIDDEN
    }

    override fun showAddButton() {
        noteActionButton.state = NoteFloatingActionButton.State.ADD
    }

    override fun showDoneButton() {
        noteActionButton.state = NoteFloatingActionButton.State.DONE
    }

    override fun showCancelButton() {
        noteActionButton.state = NoteFloatingActionButton.State.CANCEL
    }

    override fun onBottomSheetHidden() {
        dimBackgroundView.hide()
        elevationView.visibility = View.INVISIBLE
        presenter.insertNoteScreenHidden()
    }

    override fun onBottomSheetShown() {
        dimBackgroundView.show()
        elevationView.visibility = View.VISIBLE
        presenter.insertNoteScreenShown()
    }
}
