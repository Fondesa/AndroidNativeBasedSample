package com.fondesa.notes.ui.api.view

import android.util.Log
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior

class BottomSheetVisibleCallback(private val listener: Listener) :
    BottomSheetBehavior.BottomSheetCallback() {

    private var sheetPreviouslyHidden = true

    override fun onSlide(view: View, slideOffset: Float) {
        val isHidden = slideOffset == -1f
        if (isHidden && !sheetPreviouslyHidden) {
            listener.onBottomSheetHidden()
            sheetPreviouslyHidden = true
        } else if (!isHidden && sheetPreviouslyHidden) {
            listener.onBottomSheetShown()
            sheetPreviouslyHidden = false
        }
    }

    override fun onStateChanged(view: View, state: Int) {
        Log.d("LYRA", state.toString())
    }

    interface Listener {
        fun onBottomSheetHidden()
        fun onBottomSheetShown()
    }
}