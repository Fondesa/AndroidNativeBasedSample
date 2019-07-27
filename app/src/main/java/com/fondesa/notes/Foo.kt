package com.fondesa.notes

/**
 * Created by antoniolig on 2019-07-07.
 */
object Foo {

    external fun foo(input: String): String

    init {
        System.loadLibrary("jni-wrapper")
    }
}