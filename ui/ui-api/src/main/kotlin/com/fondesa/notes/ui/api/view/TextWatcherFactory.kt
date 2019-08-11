package com.fondesa.notes.ui.api.view

import android.text.TextWatcher

interface TextWatcherFactory {

    fun create(block: (String) -> Unit): TextWatcher
}