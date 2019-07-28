package com.fondesa.notes.notes.impl

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NoteFloatingActionButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FloatingActionButton(context, attrs, defStyleAttr), View.OnClickListener {

    var state: State = State.UNDEFINED
        set(value) {
            val image = when (value) {
                State.ADD -> drawableAdd
                State.CANCEL -> drawableCancel
                State.DONE -> drawableDone
                State.UNDEFINED -> null
            }
            setImageDrawable(image)
            field = value
        }

    private val drawableAdd by lazy { ContextCompat.getDrawable(context, R.drawable.ic_add) }
    private val drawableCancel by lazy { ContextCompat.getDrawable(context, R.drawable.ic_cancel) }
    private val drawableDone by lazy { ContextCompat.getDrawable(context, R.drawable.ic_done) }
    private val listeners = mutableMapOf<State, () -> Unit>()

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
        setClickListenerForState(State.ADD, listener)
    }

    fun setOnCancelClickListener(listener: () -> Unit) {
        setClickListenerForState(State.CANCEL, listener)
    }

    fun setOnDoneClickListener(listener: () -> Unit) {
        setClickListenerForState(State.DONE, listener)
    }

    private fun setClickListenerForState(state: State, listener: () -> Unit) {
        listeners[state] = listener
    }

    enum class State {
        UNDEFINED,
        ADD,
        CANCEL,
        DONE
    }
}