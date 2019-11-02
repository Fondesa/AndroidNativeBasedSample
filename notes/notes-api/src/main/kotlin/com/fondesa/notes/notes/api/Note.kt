package com.fondesa.notes.notes.api

import java.util.*

data class Note(
    val id: Int,
    val title: String,
    val description: String,
    val lastUpdateDate: Date
)