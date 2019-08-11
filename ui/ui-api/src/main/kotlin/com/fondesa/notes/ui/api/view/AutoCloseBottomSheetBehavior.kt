package com.fondesa.notes.ui.api.view

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior

class AutoCloseBottomSheetBehavior<V : View> : BottomSheetBehavior<V> {

    private val outsideRect = Rect()

    constructor() : super()

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    init {
        // This behavior sets the bottom sheet as hideable because when the user clicks outside,
        // the bottom sheet's state is set to hidden.
        isHideable = true
    }

    override fun onInterceptTouchEvent(
        parent: CoordinatorLayout,
        child: V,
        event: MotionEvent
    ): Boolean {
        val sheetState = state
        if (event.action != MotionEvent.ACTION_DOWN || sheetState == STATE_HIDDEN) {
            // Don't intercept anything when the bottom sheet is hidden or the action isn't a touch down.
            return super.onInterceptTouchEvent(parent, child, event)
        }

        if (sheetState == STATE_COLLAPSED ||
            sheetState == STATE_EXPANDED ||
            sheetState == STATE_HALF_EXPANDED
        ) {
            child.getGlobalVisibleRect(outsideRect)

            if (!outsideRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                Log.d("LYRA", "CLICKED OUTSIDE")
                // Hide the bottom sheet when the user clicks outside.
                state = STATE_HIDDEN
            }
        }
        return super.onInterceptTouchEvent(parent, child, event)
    }
}