package com.fondesa.androidnativebasedsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val input = "_input_string_"
        val output = Foo.foo(input)
        textView.text = output

        val repository = NoteRepository()
        repository.insert(DraftNote("first-title", "first-description"))
        repository.insert(DraftNote("second-title", "second-description"))
        val notes = repository.getAll()
        repository.remove(6)
    }
}
