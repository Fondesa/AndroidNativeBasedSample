package com.fondesa.notes.notes.impl

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.fondesa.notes.notes.api.Draft
import com.fondesa.notes.notes.api.Note
import com.fondesa.notes.notes.api.NotesInteractor
import com.fondesa.notes.thread.api.CoroutineContextProvider
import com.fondesa.notes.ui.api.scope.ScreenScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@ScreenScope
class NotesPresenter @Inject constructor(
    private val view: NotesContract.View,
    private val notesInteractor: NotesInteractor,
    private val contextProvider: CoroutineContextProvider
) : NotesContract.Presenter,
    LifecycleObserver,
    CoroutineScope {

    private val job = Job()
    private val noteScreenContent = NoteScreenContent(
        title = "",
        description = ""
    )
    private var buttonState = NoteButtonState.UNDEFINED
        set(value) {
            view.renderButtonState(value)
            field = value
        }

    private var isNoteScreenShown: Boolean = false
    private var pendingNoteScreenId: Int? = null

    private val originalNoteScreenContent: OriginalNoteScreenContent
        get() = _originalNoteScreenContent
            ?: throw IllegalStateException("The note screen wasn't shown yet.")

    private var _originalNoteScreenContent: OriginalNoteScreenContent? = null

    override val coroutineContext: CoroutineContext
        get() = job + contextProvider.IO

    override fun attach() {
        buttonState = NoteButtonState.ADD
        val notes = notesInteractor.getAllNotes()
        if (notes.isEmpty()) {
            view.hideListContainer()
            view.showZeroElementsView()
        } else {
            view.hideZeroElementsView()
            view.showListContainer()
            view.showNoteList(notes)
        }
    }

    override fun addButtonClicked() {
        // Since we are creating a new note, we don't need any id.
        pendingNoteScreenId = null

        val newDraft = notesInteractor.getNewDraft()

        _originalNoteScreenContent = OriginalNoteScreenContent(
            title = "",
            description = ""
        )

        val title: String
        val description: String
        if (newDraft != null) {
            title = newDraft.title
            description = newDraft.description
        } else {
            title = ""
            description = ""
        }

        view.showNoteScreenTitle(title)
        view.showNoteScreenDescription(description)
        view.showNoteScreen()
    }

    override fun doneButtonClicked() {
        view.hideNoteScreen()

        val draft = noteScreenContent.toDraft()

        val pendingNoteScreenId = pendingNoteScreenId
        if (pendingNoteScreenId != null) {
            // Update the note.
            notesInteractor.updateNote(pendingNoteScreenId, draft)
        } else {
            // Insert the note.
            notesInteractor.insertNote(draft)
        }

        val notes = notesInteractor.getAllNotes()
        view.hideZeroElementsView()
        view.showListContainer()
        view.showNoteList(notes)
    }

    override fun cancelButtonClicked() {
        view.hideNoteScreen()
    }

    override fun backPressed() {
        if (isNoteScreenShown) {
            view.hideNoteScreen()
        } else {
            view.executeBackPress()
        }
    }

    override fun noteScreenShown() {
        isNoteScreenShown = true
        buttonState = if (noteScreenContent.isValid) {
            NoteButtonState.DONE
        } else {
            NoteButtonState.CANCEL
        }
    }

    override fun noteScreenHidden() {
        isNoteScreenShown = false
        buttonState = NoteButtonState.ADD
    }

    override fun noteScreenTitleChanged(title: String) {
        noteScreenContent.title = title
        toggleDraftLabel()
        buttonState = if (noteScreenContent.isValid) {
            NoteButtonState.DONE
        } else {
            NoteButtonState.CANCEL
        }
        val pendingNoteScreenId = pendingNoteScreenId
        if (pendingNoteScreenId != null) {
            notesInteractor.updateExistingDraftTitle(pendingNoteScreenId, title)
        } else {
            notesInteractor.updateNewDraftTitle(title)
        }
    }

    override fun noteScreenDescriptionChanged(description: String) {
        noteScreenContent.description = description
        toggleDraftLabel()
        buttonState = if (noteScreenContent.isValid) {
            NoteButtonState.DONE
        } else {
            NoteButtonState.CANCEL
        }
        val pendingNoteScreenId = pendingNoteScreenId
        if (pendingNoteScreenId != null) {
            notesInteractor.updateExistingDraftDescription(pendingNoteScreenId, description)
        } else {
            notesInteractor.updateNewDraftDescription(description)
        }
    }

    override fun noteClicked(note: Note) {
        val (noteId, noteTitle, noteDescription) = note
        _originalNoteScreenContent = OriginalNoteScreenContent(
            title = noteTitle,
            description = noteDescription
        )

        // Save the note id to identify the note which should be updated.
        pendingNoteScreenId = noteId

        val existingDraft = notesInteractor.getExistingDraft(noteId)

        val title: String
        val description: String
        if (existingDraft != null) {
            title = existingDraft.title
            description = existingDraft.description
        } else {
            title = noteTitle
            description = noteDescription
        }

        view.showNoteScreenTitle(title)
        view.showNoteScreenDescription(description)
        view.showNoteScreen()
    }

    override fun searchQueryChanged(query: String) {
        val notes = notesInteractor.getNotesByText(query)
        if (notes.isEmpty()) {
            view.hideListContainer()
            view.showZeroElementsViewForQuery()
        } else {
            view.hideZeroElementsView()
            view.showListContainer()
            view.showNoteList(notes)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun screenEntersBackgroundState() {
        launch {
            // The changes will be persisted in background.
            notesInteractor.persistChanges()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun detach() {
        job.cancel()
    }

    private fun toggleDraftLabel() {
        val isOriginalTitle = noteScreenContent.title == originalNoteScreenContent.title
        val isOriginalDescription =
            noteScreenContent.description == originalNoteScreenContent.description
        if (isOriginalTitle && isOriginalDescription) {
            view.hideDraftLabel()
        } else {
            view.showDraftLabel()
        }
    }

    private data class NoteScreenContent(
        var title: String,
        var description: String
    ) {

        val isValid: Boolean get() = title.isNotBlank() || description.isNotBlank()

        fun toDraft(): Draft = Draft(title, description)
    }

    private data class OriginalNoteScreenContent(
        val title: String,
        val description: String
    )
}