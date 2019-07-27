package com.fondesa.notes.ui.api.qualifiers

import javax.inject.Scope

/**
 * Identifies a scope which lasts as a screen's life.
 */
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ScreenScope