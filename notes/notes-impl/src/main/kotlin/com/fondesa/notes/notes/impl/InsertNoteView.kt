package com.fondesa.notes.notes.impl

import android.content.Context
import android.graphics.Color
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.fondesa.notes.ui.api.util.hideKeyboard
import com.fondesa.notes.ui.api.util.inflateChild
import com.fondesa.notes.ui.api.view.ImmediateTextChangeWatcher
import com.fondesa.notes.ui.api.view.AutoCloseBottomSheetBehavior
import com.fondesa.notes.ui.api.view.BottomSheetVisibleCallback
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.sheet_insert_note.view.*

class InsertNoteView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr),
    CoordinatorLayout.AttachedBehavior,
    BottomSheetVisibleCallback.Listener {

    private val behavior: BottomSheetBehavior<View> =
        if (attrs == null) {
            AutoCloseBottomSheetBehavior()
        } else {
            AutoCloseBottomSheetBehavior(context, attrs)
        }

    private var titleChangeListener: TextWatcher? = null
    private var descriptionChangeListener: TextWatcher? = null
    private var visibilityListener: VisibilityListener? = null

    init {
        inflateChild(R.layout.sheet_insert_note, attachToRoot = true)

        setBackgroundColor(Color.WHITE)
        val padding = resources.getDimensionPixelSize(R.dimen.space_lg)
        setPadding(padding, 0, padding, 0)
        orientation = VERTICAL

        // Prevent the EditText to gain the focus when this view is shown.
        isFocusable = true
        isFocusableInTouchMode = true

        hide()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        clearOnTitleChangeListener()
        clearOnDescriptionChangeListener()
        behavior.setBottomSheetCallback(null)
        visibilityListener = null
    }

    override fun getBehavior(): CoordinatorLayout.Behavior<*> = behavior

    override fun onBottomSheetHidden() {
        clearFocus()
        hideKeyboard()
        visibilityListener?.onInsertNoteViewHidden()
    }

    override fun onBottomSheetShown() {
        visibilityListener?.onInsertNoteViewShown()
    }

    fun setTitle(title: String) {
        titleTextView.setText(title)
    }

    fun setDescription(description: String) {
        descriptionTextView.setText(description)
    }

    fun showCollapsed() {
        behavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    fun hide() {
        behavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    fun setOnTitleChangeListener(listener: (String) -> Unit) {
        clearOnTitleChangeListener()
        titleChangeListener = ImmediateTextChangeWatcher(listener).also {
            titleTextView.addTextChangedListener(it)
        }
    }

    fun setOnDescriptionChangeListener(listener: (String) -> Unit) {
        clearOnDescriptionChangeListener()
        descriptionChangeListener = ImmediateTextChangeWatcher(listener).also {
            descriptionTextView.addTextChangedListener(it)
        }
    }

    fun setOnVisibilityListener(listener: VisibilityListener) {
        visibilityListener = listener
        // Register the callback for the bottom sheet.
        behavior.setBottomSheetCallback(BottomSheetVisibleCallback(this))
    }

    private fun clearOnTitleChangeListener() {
        titleChangeListener?.let {
            titleTextView.removeTextChangedListener(it)
        }
        titleChangeListener = null
    }

    private fun clearOnDescriptionChangeListener() {
        descriptionChangeListener?.let {
            descriptionTextView.removeTextChangedListener(it)
        }
        descriptionChangeListener = null
    }

    interface VisibilityListener {

        fun onInsertNoteViewHidden()

        fun onInsertNoteViewShown()
    }
}
