package com.fondesa.notes.ui.api.util

import android.app.Activity
import android.content.ContextWrapper
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.annotation.LayoutRes


/**
 * Inflates a layout inside the receiver.
 *
 * @param layout the layout resource which should be inflated.
 * @param attachToRoot true if the layout should be added to the receiver, false if it should only
 * infers its layout params.
 */
fun ViewGroup.inflateChild(@LayoutRes layout: Int, attachToRoot: Boolean = false): View =
    LayoutInflater.from(context).inflate(layout, this, attachToRoot)

fun TextView.setTextChangedListener(watcher: TextWatcher) {
    removeTextChangedListener(watcher)
    addTextChangedListener(watcher)
}

val View.activity: Activity?
    get() {
        var context = context
        while (context is ContextWrapper) {
            if (context is Activity) {
                return context
            }
            context = context.baseContext
        }
        return null
    }

val View.window: Window? get() = activity?.window