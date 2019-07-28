package com.fondesa.notes.notes.impl

import com.fondesa.notes.notes.api.Note
import com.fondesa.notes.ui.api.mvp.BasePresenter

/**
 * Contract between the view and the presenter for the note list screen.
 */
object NotesContract {

    /**
     * Defines all the actions the view should perform when the presenter notifies them.
     */
    interface View {

        /**
         * Shows the container of the notes.
         */
        fun showListContainer()

        /**
         * Hides the container of the notes.
         */
        fun hideListContainer()

        /**
         * Shows the view which indicates there are no notes inserted by the user.
         */
        fun showZeroElementsView()

        /**
         * Hides the view which indicates there are no notes inserted by the user.
         */
        fun hideZeroElementsView()

        /**
         * Updates the current list of notes with the given one.
         *
         * @param noteList the new list of notes which should be shown.
         */
        fun showNoteList(noteList: List<Note>)

        fun showInsertNoteScreen()

        fun hideInsertNoteScreen()
    }

    /**
     * Defines all the actions the presenter should perform when the view notifies them.
     */
    interface Presenter : BasePresenter {

        fun addButtonClicked()

        fun doneButtonClicked()
    }
}