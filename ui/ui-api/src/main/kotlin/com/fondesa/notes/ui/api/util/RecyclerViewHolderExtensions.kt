package com.fondesa.notes.ui.api.util

import android.content.Context
import androidx.recyclerview.widget.RecyclerView

/**
 * Gets the [Context] inside a [RecyclerView.ViewHolder].
 */
val RecyclerView.ViewHolder.context: Context get() = itemView.context