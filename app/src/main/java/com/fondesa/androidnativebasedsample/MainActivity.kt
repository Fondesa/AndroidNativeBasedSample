package com.fondesa.androidnativebasedsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val input = "_input_string_"
        val output = Foo.foo(input)
        textView.text = output

        val repository = NoteRepository()
        val notes = repository.getAll()
        repository.insert(DraftNote("first-title", "first-description"))
        repository.insert(DraftNote("second-title", "second-description"))
        repository.remove(6)
    }
}
