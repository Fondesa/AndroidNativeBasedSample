package com.fondesa.notes.ui.api.view

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import androidx.appcompat.widget.AppCompatEditText
import com.fondesa.notes.ui.api.R

class AutoClearFocusEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.editTextStyle
) : AppCompatEditText(context, attrs, defStyleAttr) {

    private var autoClearFocusImeActions: IntArray? = null

    fun clearFocusOnImeActions(vararg imeActions: Int) {
        autoClearFocusImeActions = imeActions
    }

    override fun onKeyPreIme(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
            clearComposingText()
            // The method clearComposingText() changes the spans of the current text.
            // It should be set again to see the new text changes on the UI.
            text = text
            clearFocus()
        }
        return super.dispatchKeyEvent(event)
    }

    override fun onEditorAction(actionCode: Int) {
        autoClearFocusImeActions?.takeIf {
            it.contains(actionCode)
        }?.let {
            clearComposingText()
            // The method clearComposingText() changes the spans of the current text.
            // It should be set again to see the new text changes on the UI.
            text = text
            clearFocus()
        }
        super.onEditorAction(actionCode)
    }
}