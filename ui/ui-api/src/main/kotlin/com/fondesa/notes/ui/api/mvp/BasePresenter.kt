package com.fondesa.notes.ui.api.mvp

/**
 * Defines the presenter in an MVP pattern.
 * The presenter is used to define the logic between the model and the view.
 * It mustn't have any relationship with the Android code to be tested with jUnit tests.
 */
interface BasePresenter {

    /**
     * Attaches the presenter to manage the communication between the model and the view.
     */
    fun attach()
}