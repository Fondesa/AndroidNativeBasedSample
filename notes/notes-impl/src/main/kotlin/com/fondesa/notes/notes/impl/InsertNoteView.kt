package com.fondesa.notes.notes.impl

import android.content.Context
import android.graphics.Color
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.fondesa.notes.ui.api.util.inflateChild
import com.fondesa.notes.ui.api.view.AfterTextChangedWatcher
import com.fondesa.notes.ui.api.view.AutoCloseBottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.sheet_insert_note.view.*

class InsertNoteView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), CoordinatorLayout.AttachedBehavior {

    private val behavior: BottomSheetBehavior<View> =
        if (attrs == null) {
            AutoCloseBottomSheetBehavior()
        } else {
            AutoCloseBottomSheetBehavior(context, attrs)
        }

    private var titleChangeListener: TextWatcher? = null
    private var descriptionChangeListener: TextWatcher? = null

    init {
        inflateChild(R.layout.sheet_insert_note, attachToRoot = true)

        setBackgroundColor(Color.WHITE)
        val padding = resources.getDimensionPixelSize(R.dimen.space_lg)
        setPadding(padding, 0, padding, 0)
        orientation = VERTICAL
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        clearOnTitleChangeListener()
        clearOnDescriptionChangeListener()
    }

    override fun getBehavior(): CoordinatorLayout.Behavior<*> = behavior

    fun setTitle(title: String) {
        titleTextView.setText(title)
    }

    fun setDescription(description: String) {
        descriptionTextView.setText(description)
    }

    fun setOnTitleChangeListener(listener: (String) -> Unit) {
        clearOnTitleChangeListener()
        titleChangeListener = AfterTextChangedWatcher(listener).also {
            titleTextView.addTextChangedListener(it)
        }
    }

    fun setOnDescriptionChangeListener(listener: (String) -> Unit) {
        clearOnDescriptionChangeListener()
        descriptionChangeListener = AfterTextChangedWatcher(listener).also {
            descriptionTextView.addTextChangedListener(it)
        }
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
}
