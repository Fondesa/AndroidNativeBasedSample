package com.fondesa.notes.ui.api.view

import android.util.Log
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior

class BottomSheetVisibleCallback(private val listener: Listener) :
    BottomSheetBehavior.BottomSheetCallback() {

    private var sheetPreviouslyHidden = true

    override fun onSlide(view: View, slideOffset: Float) {
        if (slideOffset.isNaN()) {
            // On certain APIs the slide offset can be NaN when it reached the maximum height.
            // In this case, it shouldn't be computed since this callback will be invoked with NaN only
            // if previously it has been invoked with 1.0f (apparently).
            return
        }
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