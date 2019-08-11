package com.fondesa.notes.notes.impl

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleObserver
import androidx.recyclerview.widget.DividerItemDecoration
import com.fondesa.notes.log.api.Log
import com.fondesa.notes.notes.api.Note
import com.fondesa.notes.ui.api.util.hideKeyboard
import com.fondesa.notes.ui.api.view.BottomSheetVisibleCallback
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_notes.*
import kotlinx.android.synthetic.main.sheet_insert_note.*
import javax.inject.Inject

class NotesActivity : AppCompatActivity(),
    NotesContract.View,
    BottomSheetVisibleCallback.Listener,
    NoteRecyclerViewAdapter.OnNoteClickListener {

    @Inject
    internal lateinit var presenter: NotesContract.Presenter

    @Inject
    internal lateinit var lifecycleObservers: Set<@JvmSuppressWildcards LifecycleObserver>

    @Inject
    internal lateinit var adapter: NoteRecyclerViewAdapter

    private val noteSheet by lazy { insertNoteView.behavior as BottomSheetBehavior<*> }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        lifecycleObservers.forEach {
            lifecycle.addObserver(it)
        }

        noteSheet.state = BottomSheetBehavior.STATE_HIDDEN
        noteSheet.setBottomSheetCallback(BottomSheetVisibleCallback(this))

        noteActionButton.setOnAddClickListener(presenter::addButtonClicked)
        noteActionButton.setOnDoneClickListener(presenter::doneButtonClicked)
        noteActionButton.setOnCancelClickListener(presenter::cancelButtonClicked)
        insertNoteView.setOnTitleChangeListener(presenter::noteScreenTitleChanged)
        insertNoteView.setOnDescriptionChangeListener(presenter::noteScreenDescriptionChanged)

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

    override fun showDraftLabel() {
        draftLabelView.visibility = View.VISIBLE
    }

    override fun hideDraftLabel() {
        draftLabelView.visibility = View.INVISIBLE
    }

    override fun showNoteList(noteList: List<Note>) {
        adapter.updateList(noteList)
    }

    override fun showNoteScreen() {
        Log.d("LYRA show note screen")
        noteSheet.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    override fun hideNoteScreen() {
        Log.d("LYRA hide note screen")
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
        Log.d("LYRA sheet hidden")

        hideKeyboard()
        dimBackgroundView.hide()
        elevationView.visibility = View.INVISIBLE
        presenter.noteScreenHidden()
    }

    override fun onBottomSheetShown() {
        Log.d("LYRA sheet shown")

        dimBackgroundView.show()
        elevationView.visibility = View.VISIBLE
        presenter.noteScreenShown()
    }

    override fun onNoteClicked(note: Note) {
        presenter.noteClicked(note)
    }
}
