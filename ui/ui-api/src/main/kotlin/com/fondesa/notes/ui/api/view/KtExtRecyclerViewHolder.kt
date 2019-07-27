package com.fondesa.notes.ui.api.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

/**
 * Implementation of [RecyclerView.ViewHolder] which adds the support for Kotlin extensions.
 *
 * @param containerView the holder's root view.
 */
abstract class KtExtRecyclerViewHolder(override val containerView: View) :
    RecyclerView.ViewHolder(containerView),
    LayoutContainer