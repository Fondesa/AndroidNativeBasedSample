package com.fondesa.notes.notes.impl

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.fondesa.notes.notes.api.DraftNote
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_notes.*
import kotlinx.android.synthetic.main.sheet_insert_note.*

class NotesActivity : AppCompatActivity() {

    private val noteSheet by lazy { BottomSheetBehavior.from(insertNoteContainer) }
    private val drawableAdd by lazy { ContextCompat.getDrawable(this, R.drawable.ic_add) }
    private val drawableDone by lazy { ContextCompat.getDrawable(this, R.drawable.ic_done) }
    private var sheetPreviouslyHidden = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        noteSheet.state = BottomSheetBehavior.STATE_HIDDEN
        noteSheet.setBottomSheetCallback(BottomSheetCallback())

        buttonAdd.setImageDrawable(drawableAdd)
        buttonAdd.setOnClickListener {
            val state = noteSheet.state
            if (state == BottomSheetBehavior.STATE_COLLAPSED) {
                onDoneButtonClick()
            } else if (state == BottomSheetBehavior.STATE_HIDDEN) {
                onAddButtonClick()
            }
        }

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
    }

    private fun onAddButtonClick() {
        noteSheet.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun onDoneButtonClick() {
        noteSheet.state = BottomSheetBehavior.STATE_HIDDEN
    }

    private fun onBottomSheetHidden() {
        dimBackgroundView.hide()
        elevationView.visibility = View.INVISIBLE
        buttonAdd.setImageDrawable(drawableAdd)
    }

    private fun onBottomSheetShown() {
        dimBackgroundView.show()
        elevationView.visibility = View.VISIBLE
        buttonAdd.setImageDrawable(drawableDone)
    }

    private inner class BottomSheetCallback : BottomSheetBehavior.BottomSheetCallback() {

        override fun onSlide(view: View, slideOffset: Float) {

        }

        @SuppressLint("SwitchIntDef")
        override fun onStateChanged(view: View, state: Int) {
            when {
                state == BottomSheetBehavior.STATE_HIDDEN && !sheetPreviouslyHidden -> {
                    onBottomSheetHidden()
                    sheetPreviouslyHidden = true
                }

                state != BottomSheetBehavior.STATE_HIDDEN && sheetPreviouslyHidden -> {
                    onBottomSheetShown()
                    sheetPreviouslyHidden = false
                }
            }
        }
    }
}
