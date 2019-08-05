package com.fondesa.notes.notes.impl

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import com.fondesa.notes.notes.api.Note
import com.fondesa.notes.ui.api.util.hideKeyboard
import com.fondesa.notes.ui.api.view.BottomSheetVisibleCallback
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_notes.*
import javax.inject.Inject

class NotesActivity : AppCompatActivity(),
    NotesContract.View,
    BottomSheetVisibleCallback.Listener,
    NoteRecyclerViewAdapter.OnNoteClickListener {

    @Inject
    internal lateinit var presenter: NotesContract.Presenter

    @Inject
    internal lateinit var adapter: NoteRecyclerViewAdapter

    private val noteSheet by lazy { insertNoteView.behavior as BottomSheetBehavior<*> }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        noteSheet.state = BottomSheetBehavior.STATE_HIDDEN
        noteSheet.setBottomSheetCallback(BottomSheetVisibleCallback(this))

        noteActionButton.setOnAddClickListener(presenter::addButtonClicked)
        noteActionButton.setOnDoneClickListener(presenter::doneButtonClicked)
        noteActionButton.setOnCancelClickListener(presenter::cancelButtonClicked)
        insertNoteView.setOnTitleChangeListener(presenter::noteScreenTitleChanged)
        insertNoteView.setOnDescriptionChangeListener(presenter::noteScreenDescriptionChanged)
        dimBackgroundView.setOnClickListener {
            presenter.pressedOutsideNoteScreen()
        }

        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        // Set the adapter on the RecyclerView.
        recyclerView.adapter = adapter

        presenter.attach()
    }

    override fun onBackPressed() {
        presenter.backPressed()
    }

    override fun executeBackPress() {
        super.onBackPressed()
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

    override fun showNoteScreen() {
        noteSheet.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    override fun hideNoteScreen() {
        noteSheet.state = BottomSheetBehavior.STATE_HIDDEN
    }

    override fun renderButtonState(state: NoteButtonState) {
        noteActionButton.state = state
    }

    override fun showNoteScreenTitle(title: String) {
        insertNoteView.setTitle(title)
    }

    override fun showNoteScreenDescription(description: String) {
        insertNoteView.setDescription(description)
    }

    override fun onBottomSheetHidden() {
        hideKeyboard()
        dimBackgroundView.hide()
        elevationView.visibility = View.INVISIBLE
        presenter.noteScreenHidden()
    }

    override fun onBottomSheetShown() {
        dimBackgroundView.show()
        elevationView.visibility = View.VISIBLE
        presenter.noteScreenShown()
    }

    override fun onNoteClicked(note: Note) {
        presenter.noteClicked(note)
    }
}
