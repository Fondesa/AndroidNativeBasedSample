package com.fondesa.notes.notes.impl

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleObserver
import androidx.recyclerview.widget.DividerItemDecoration
import com.fondesa.notes.log.api.Log
import com.fondesa.notes.notes.api.Note
import com.fondesa.notes.ui.api.util.hideWithCircularAnim
import com.fondesa.notes.ui.api.util.showWithCircularAnim
import com.fondesa.notes.ui.api.view.DelayedTextWatcher
import com.fondesa.notes.ui.api.view.TextWatcherFactory
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_notes.*
import kotlinx.android.synthetic.main.sheet_insert_note.*
import javax.inject.Inject

class NotesActivity : AppCompatActivity(),
    NotesContract.View,
    NoteRecyclerViewAdapter.OnNoteClickListener,
    InsertNoteView.VisibilityListener {

    @Inject
    internal lateinit var presenter: NotesContract.Presenter

    @Inject
    internal lateinit var lifecycleObservers: Set<@JvmSuppressWildcards LifecycleObserver>

    @Inject
    internal lateinit var adapter: NoteRecyclerViewAdapter

    @Inject
    @DelayedTextWatcher
    internal lateinit var delayedTextWatcherFactory: TextWatcherFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        val searchTextWatcher = delayedTextWatcherFactory.create {

        }

        lifecycleObservers.forEach {
            lifecycle.addObserver(it)
        }

        noteActionButton.setOnAddClickListener(presenter::addButtonClicked)
        noteActionButton.setOnDoneClickListener(presenter::doneButtonClicked)
        noteActionButton.setOnCancelClickListener(presenter::cancelButtonClicked)
        insertNoteView.setOnTitleChangeListener(presenter::noteScreenTitleChanged)
        insertNoteView.setOnDescriptionChangeListener(presenter::noteScreenDescriptionChanged)
        insertNoteView.setOnVisibilityListener(this)

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
        insertNoteView.showCollapsed()
    }

    override fun hideNoteScreen() {
        Log.d("LYRA hide note screen")
        insertNoteView.hide()
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

    override fun onInsertNoteViewHidden() {
        Log.d("LYRA sheet hidden")

        toolbarContainer.showWithCircularAnim()
        dimBackgroundView.hide()
        elevationView.visibility = View.INVISIBLE
        presenter.noteScreenHidden()
    }

    override fun onInsertNoteViewShown() {
        Log.d("LYRA sheet shown")

        toolbarContainer.hideWithCircularAnim()
        dimBackgroundView.show()
        elevationView.visibility = View.VISIBLE
        presenter.noteScreenShown()
    }

    override fun onNoteClicked(note: Note) {
        presenter.noteClicked(note)
    }
}
