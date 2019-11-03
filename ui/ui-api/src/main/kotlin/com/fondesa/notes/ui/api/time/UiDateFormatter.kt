package com.fondesa.notes.ui.api.time

import java.util.*

interface UiDateFormatter {

    fun formatToReadableDate(date: Date): String
}