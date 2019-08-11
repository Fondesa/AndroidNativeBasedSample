package com.fondesa.notes.ui.api.view

import android.text.TextWatcher
import javax.inject.Qualifier

/**
 * Identifies the implementation of a [TextWatcher] which notifies the text change immediately.
 */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ImmediateTextWatcher