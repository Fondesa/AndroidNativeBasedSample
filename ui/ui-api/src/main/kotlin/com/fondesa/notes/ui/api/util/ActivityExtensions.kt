package com.fondesa.notes.ui.api.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager

/**
 * Hides the software keyboard, if shown.
 */
fun Activity.hideKeyboard() {
    currentFocus?.internalHideKeyboard()
}

fun View.hideKeyboard() {
    val focusedView = (this as? ViewGroup)?.focusedChild ?: this
    focusedView.internalHideKeyboard()
}

private fun View.internalHideKeyboard() {
    val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    // Hide the keyboard.
    inputManager.hideSoftInputFromWindow(
        windowToken,
        InputMethodManager.HIDE_NOT_ALWAYS
    )
}