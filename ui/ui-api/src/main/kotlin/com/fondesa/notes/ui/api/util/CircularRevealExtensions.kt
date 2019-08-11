package com.fondesa.notes.ui.api.util

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import com.fondesa.notes.ui.api.anim.AnimationDuration
import com.google.android.material.circularreveal.CircularRevealCompat
import com.google.android.material.circularreveal.CircularRevealWidget
import kotlin.math.pow
import kotlin.math.sqrt

fun <V> V.hideWithCircularAnim() where V : View, V : CircularRevealWidget {
    post {
        val viewWidth = width.toDouble()
        val viewHeight = height.toDouble()

        val viewDiagonal = sqrt(viewWidth.pow(2) + viewHeight.pow(2))

        val animator = CircularRevealCompat.createCircularReveal(
            this,
            (viewWidth / 2).toFloat(),
            (viewHeight / 2).toFloat(),
            (viewDiagonal / 2).toFloat(),
            0f
        )

        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                visibility = View.INVISIBLE
            }
        })

        animator.duration = AnimationDuration.SHORT.toLong()
        animator.start()
    }
}

fun <V> V.showWithCircularAnim() where V : View, V : CircularRevealWidget {
    post {
        val viewWidth = width.toDouble()
        val viewHeight = height.toDouble()

        val viewDiagonal = sqrt(viewWidth.pow(2) + viewHeight.pow(2))

        val animator = CircularRevealCompat.createCircularReveal(
            this,
            (viewWidth / 2).toFloat(),
            (viewHeight / 2).toFloat(),
            0f,
            (viewDiagonal / 2).toFloat()
        )

        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
                visibility = View.VISIBLE
            }
        })

        animator.duration = AnimationDuration.SHORT.toLong()
        animator.start()
    }
}
