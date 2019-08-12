package com.fondesa.notes.notes.impl

import android.content.Context
import android.util.AttributeSet
import com.fondesa.notes.ui.api.util.hideWithCircularAnim
import com.fondesa.notes.ui.api.util.inflateChild
import com.fondesa.notes.ui.api.util.showWithCircularAnim
import com.google.android.material.circularreveal.cardview.CircularRevealCardView

class SearchView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    CircularRevealCardView(context, attrs) {

    init {
        inflateChild(R.layout.search_view, attachToRoot = true)

        radius = resources.getDimension(R.dimen.radius_lg)
        cardElevation = resources.getDimension(R.dimen.search_card_elevation)
    }

    fun show() {
        showWithCircularAnim()
    }

    fun hide() {
        hideWithCircularAnim()
    }
}