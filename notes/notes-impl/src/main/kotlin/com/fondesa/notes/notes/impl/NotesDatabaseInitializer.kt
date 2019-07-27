package com.fondesa.notes.notes.impl

import android.content.Context
import com.fondesa.notes.core.api.AppInitializer
import dagger.Reusable
import javax.inject.Inject

@Reusable
class NotesDatabaseInitializer @Inject constructor(private val context: Context) : AppInitializer {

    override fun initialize() {
        val dbPath = context.filesDir.absolutePath + "/notes.db"
        initializeDatabase(dbPath = dbPath)
    }

    private external fun initializeDatabase(dbPath: String): Long
}