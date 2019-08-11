package com.fondesa.notes.ui.api.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.fondesa.notes.ui.api.R
import com.fondesa.notes.ui.api.anim.AnimationDuration

class BottomSheetBackgroundView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val animator by lazy { animate().setDuration(AnimationDuration.SHORT.toLong()) }
    private val onHideAnimationListener by lazy {
        object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                // When the view is not visible, it shouldn't intercept any touch event.
                isClickable = false
            }
        }
    }

    init {
        alpha = 0f
        // By default, this view won't intercept any touch event.
        isClickable = false

        @ColorInt val color = ContextCompat.getColor(context, R.color.dim_background)
        setBackgroundColor(color)
    }

    fun show() {
        if (alpha == 1f) {
            return
        }
        // When the view is visible, all the clicks are intercepted by this view because the views
        // behind the bottom sheet shouldn't handle any event.
        isClickable = true
        animator.alpha(1f)
            .setListener(null)
            .start()
    }

    fun hide() {
        if (alpha == 0f) {
            return
        }
        animator.alpha(0f)
            .setListener(onHideAnimationListener)
            .start()
    }
}