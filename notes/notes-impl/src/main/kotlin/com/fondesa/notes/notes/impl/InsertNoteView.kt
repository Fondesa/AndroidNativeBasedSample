package com.fondesa.notes.notes.impl

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.fondesa.notes.notes.api.DraftNote
import com.fondesa.notes.notes.api.Note
import com.fondesa.notes.ui.api.util.inflateChild
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.sheet_insert_note.view.*

class InsertNoteView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), CoordinatorLayout.AttachedBehavior {

    val isContentValid: Boolean
        get() {
            val title = titleTextView.text.toString()
            val description = descriptionTextView.text.toString()
            return title.isNotBlank() && description.isNotBlank()
        }

    private val behavior: BottomSheetBehavior<View> =
        if (attrs == null) {
            BottomSheetBehavior()
        } else {
            BottomSheetBehavior<View>(context, attrs)
        }.apply {
            isHideable = true
        }

    init {
        inflateChild(R.layout.sheet_insert_note, attachToRoot = true)

        setBackgroundColor(Color.WHITE)
        val padding = resources.getDimensionPixelSize(R.dimen.space_lg)
        setPadding(padding, 0, padding, 0)
        orientation = VERTICAL
    }

    override fun getBehavior(): CoordinatorLayout.Behavior<*> = behavior

    fun bindContent(note: Note) {
        titleTextView.setText(note.title)
        descriptionTextView.setText(note.description)
    }

    fun clearContent() {
        titleTextView.text = null
        descriptionTextView.text = null
    }

    fun obtainContent(): DraftNote = DraftNote(
        title = titleTextView.text.toString(),
        description = descriptionTextView.text.toString()
    )
}
