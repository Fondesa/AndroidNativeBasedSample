package com.fondesa.notes.notes.impl

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fondesa.notes.notes.api.DraftNote

class NotesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        val repository = NativeNotesRepository()
        repository.insert(
            DraftNote(
                "first-title",
                "first-description"
            )
        )
        repository.insert(
            DraftNote(
                "second-title",
                "second-description"
            )
        )
        val notes = repository.getAll()
        repository.update(
            notes[1].id,
            DraftNote(
                "updated-second-title",
                "updated-second-description"
            )
        )
        val notesAfterUpdate = repository.getAll()
        repository.remove(notes[0].id)
        val notesAfterRemove = repository.getAll()
    }
}
