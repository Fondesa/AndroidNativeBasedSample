package com.fondesa.notes.ui.api.view

import android.annotation.SuppressLint
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior

class BottomSheetVisibleCallback(private val listener: Listener) :
    BottomSheetBehavior.BottomSheetCallback() {

    private var sheetPreviouslyHidden = true

    override fun onSlide(view: View, slideOffset: Float) = Unit

    @SuppressLint("SwitchIntDef")
    override fun onStateChanged(view: View, state: Int) {
        when {
            state == BottomSheetBehavior.STATE_HIDDEN && !sheetPreviouslyHidden -> {
                listener.onBottomSheetHidden()
                sheetPreviouslyHidden = true
            }

            state != BottomSheetBehavior.STATE_HIDDEN && sheetPreviouslyHidden -> {
                listener.onBottomSheetShown()
                sheetPreviouslyHidden = false
            }
        }
    }

    interface Listener {
        fun onBottomSheetHidden()
        fun onBottomSheetShown()
    }
}