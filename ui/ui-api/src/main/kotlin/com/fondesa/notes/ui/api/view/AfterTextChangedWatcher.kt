package com.fondesa.notes.ui.api.view

import android.text.Editable
import android.text.TextWatcher

class AfterTextChangedWatcher(private inline val block: (String) -> Unit) : TextWatcher {

    override fun afterTextChanged(s: Editable?) {
        block(s?.toString() ?: "")
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
}