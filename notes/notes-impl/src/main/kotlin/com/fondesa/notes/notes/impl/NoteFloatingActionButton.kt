package com.fondesa.notes.notes.impl

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NoteFloatingActionButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FloatingActionButton(context, attrs, defStyleAttr), View.OnClickListener {

    var state: NoteButtonState = NoteButtonState.UNDEFINED
        set(value) {
            if (field == value) {
                return
            }
            val image = when (value) {
                NoteButtonState.ADD -> drawableAdd
                NoteButtonState.CANCEL -> drawableCancel
                NoteButtonState.DONE -> drawableDone
                NoteButtonState.UNDEFINED -> null
            }
            setImageDrawable(image)
            field = value
        }

    private val drawableAdd by lazy {
        AppCompatResources.getDrawable(context, R.drawable.ic_add)
    }
    private val drawableCancel by lazy {
        AppCompatResources.getDrawable(context, R.drawable.ic_cancel)
    }
    private val drawableDone by lazy {
        AppCompatResources.getDrawable(context, R.drawable.ic_done)
    }
    private val listeners = mutableMapOf<NoteButtonState, () -> Unit>()

    init {
        setOnClickListener(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        listeners.clear()
    }

    override fun onClick(v: View) {
        val listener = listeners[state]
        listener?.invoke()
    }

    fun setOnAddClickListener(listener: () -> Unit) {
        setClickListenerForState(NoteButtonState.ADD, listener)
    }

    fun setOnCancelClickListener(listener: () -> Unit) {
        setClickListenerForState(NoteButtonState.CANCEL, listener)
    }

    fun setOnDoneClickListener(listener: () -> Unit) {
        setClickListenerForState(NoteButtonState.DONE, listener)
    }

    private fun setClickListenerForState(state: NoteButtonState, listener: () -> Unit) {
        listeners[state] = listener
    }
}