package com.fondesa.androidnativebasedsample

import android.app.Application

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        val dbPath = filesDir.absolutePath + "/notes.db"
        initializeDatabase(dbPath = dbPath)
    }

    private external fun initializeDatabase(dbPath: String): Long

    companion object {
        init {
            System.loadLibrary("jni-wrapper")
        }
    }
}