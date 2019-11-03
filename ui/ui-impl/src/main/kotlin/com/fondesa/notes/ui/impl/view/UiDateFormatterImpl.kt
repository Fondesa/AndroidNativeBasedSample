package com.fondesa.notes.ui.impl.view

import android.content.Context
import com.fondesa.notes.time.api.isToday
import com.fondesa.notes.time.api.isYesterday
import com.fondesa.notes.ui.api.time.UiDateFormatter
import com.fondesa.notes.ui.impl.R
import dagger.Reusable
import java.text.DateFormat
import java.util.*
import javax.inject.Inject

@Reusable
class UiDateFormatterImpl @Inject constructor(context: Context) : UiDateFormatter {

    private val timeFormat by lazy { DateFormat.getTimeInstance(DateFormat.SHORT) }
    private val dateFormat by lazy { DateFormat.getDateInstance(DateFormat.SHORT) }
    private val labelYesterday by lazy { context.getString(R.string.label_yesterday) }

    override fun formatToReadableDate(date: Date): String = when {
        date.isToday -> timeFormat.format(date)
        date.isYesterday -> labelYesterday
        else -> dateFormat.format(date)
    }
}