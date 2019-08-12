package com.fondesa.notes.notes.impl

import android.content.Context
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.fondesa.notes.ui.api.injection.ViewInjection
import com.fondesa.notes.ui.api.lifecycle.StartStopLifecycleOwner
import com.fondesa.notes.ui.api.util.hideWithCircularAnim
import com.fondesa.notes.ui.api.util.inflateChild
import com.fondesa.notes.ui.api.util.setTextChangedListener
import com.fondesa.notes.ui.api.util.showWithCircularAnim
import com.fondesa.notes.ui.api.view.DelayedTextWatcher
import com.fondesa.notes.ui.api.view.TextWatcherFactory
import com.google.android.material.circularreveal.cardview.CircularRevealCardView
import kotlinx.android.synthetic.main.search_view.view.*
import javax.inject.Inject

class SearchView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    CircularRevealCardView(context, attrs),
    LifecycleOwner {

    @Inject
    @field:DelayedTextWatcher
    internal lateinit var delayedTextWatcherFactory: TextWatcherFactory

    private val lifecycleOwnerDelegate = StartStopLifecycleOwner()

    private val queryWatcher: TextWatcher by lazy {
        delayedTextWatcherFactory.create {
            queryChangeListener?.invoke(it)
        }
    }

    private var queryChangeListener: ((String) -> Unit)? = null

    init {
        ViewInjection.inject(this)

        inflateChild(R.layout.search_view, attachToRoot = true)

        radius = resources.getDimension(R.dimen.radius_lg)
        cardElevation = resources.getDimension(R.dimen.search_card_elevation)

        queryTextView.clearFocusOnImeActions(EditorInfo.IME_ACTION_DONE)
    }

    override fun getLifecycle(): Lifecycle = lifecycleOwnerDelegate.lifecycle

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        lifecycleOwnerDelegate.start()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        lifecycleOwnerDelegate.stop()
    }

    fun show() {
        showWithCircularAnim()
    }

    fun hide() {
        hideWithCircularAnim()
    }

    fun setOnQueryChangeListener(listener: (String) -> Unit) {
        queryChangeListener = listener
        queryTextView.setTextChangedListener(queryWatcher)
    }
}