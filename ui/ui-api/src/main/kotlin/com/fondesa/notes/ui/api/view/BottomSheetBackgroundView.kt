package com.fondesa.notes.ui.api.view

import android.animation.*
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.fondesa.notes.ui.api.R
import com.fondesa.notes.ui.api.anim.AnimationDuration
import com.fondesa.notes.ui.api.util.window

class BottomSheetBackgroundView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val showAnimator by lazy {
        val statusBarColorAnimator = createStatusBarColorAnimatorIfNeeded(
            from = R.color.status_bar_api_21,
            to = R.color.status_bar_dim_api_21
        )
        val alphaAnimator = createAlphaAnimator(from = 0f, to = 1f)
        if (statusBarColorAnimator == null) {
            alphaAnimator.setDefaultDuration()
            alphaAnimator.addListener(onHideAnimationListener)
            return@lazy alphaAnimator
        }
        animatorSetOf(alphaAnimator, statusBarColorAnimator).apply {
            addListener(onHideAnimationListener)
        }
    }

    private val hideAnimator by lazy {
        val statusBarColorAnimator = createStatusBarColorAnimatorIfNeeded(
            from = R.color.status_bar_dim_api_21,
            to = R.color.status_bar_api_21
        )
        val alphaAnimator = createAlphaAnimator(from = 1f, to = 0f)
        if (statusBarColorAnimator == null) {
            alphaAnimator.setDefaultDuration()
            alphaAnimator.addListener(onHideAnimationListener)
            return@lazy alphaAnimator
        }
        animatorSetOf(alphaAnimator, statusBarColorAnimator)
    }

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
        showAnimator.start()
    }

    fun hide() {
        if (alpha == 0f) {
            return
        }
        hideAnimator.start()
    }

    private fun createAlphaAnimator(from: Float, to: Float): ValueAnimator =
        ObjectAnimator.ofFloat(this, ALPHA, from, to)

    private fun createStatusBarColorAnimatorIfNeeded(@ColorRes from: Int, @ColorRes to: Int): ValueAnimator? {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP ||
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
        ) {
            // The status bar animator is needed only for API 21 and 22 because below the status bar
            // will be colored by the system and above the status bar is transparent.
            return null
        }
        // If the Window of the enclosing Activity isn't available (e.g. the Activity can't be retrieved),
        // the animator can't be returned since it can't change the status bar color.
        val window = window ?: return null
        @ColorInt val fromColor = ContextCompat.getColor(context, from)
        @ColorInt val toColor = ContextCompat.getColor(context, to)
        val animator = ValueAnimator.ofArgb(fromColor, toColor)
        animator.addUpdateListener {
            @ColorInt val animatedColor = it.animatedValue as Int
            window.statusBarColor = animatedColor
        }
        return animator
    }

    private fun Animator.setDefaultDuration() {
        duration = AnimationDuration.SHORT.toLong()
    }

    private fun animatorSetOf(vararg animators: Animator) = AnimatorSet().apply {
        playTogether(*animators)
        setDefaultDuration()
    }
}