package com.fondesa.notes.ui.api.view

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import com.fondesa.notes.ui.api.R

class BottomSheetElevationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    init {
        val shadowGradient = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM, intArrayOf(
                Color.TRANSPARENT,
                ContextCompat.getColor(context, R.color.elevation)
            )
        )
        ViewCompat.setBackground(this, shadowGradient)
    }
}