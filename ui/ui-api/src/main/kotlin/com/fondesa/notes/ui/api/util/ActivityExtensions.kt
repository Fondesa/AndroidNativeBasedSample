package com.fondesa.notes.ui.api.util

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

/**
 * Hides the software keyboard, if shown.
 */
fun Activity.hideKeyboard() {
    currentFocus?.let { focusedView ->
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        // Hide the keyboard.
        inputManager.hideSoftInputFromWindow(
            focusedView.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
}