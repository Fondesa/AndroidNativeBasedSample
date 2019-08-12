package com.fondesa.notes.ui.api.injection

import android.view.View

object ViewInjection {

    fun inject(view: View) {
        val application = view.context.applicationContext as? HasViewInjector
            ?: throw ClassCastException("The application must implement ${HasViewInjector::class.java.name}")
        application.viewInjector().inject(view)
    }
}