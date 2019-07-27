package com.fondesa.notes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fondesa.notes.notes.api.DraftNote
import com.fondesa.notes.notes.impl.NativeNotesRepository

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
