package com.fondesa.notes.ui.api.scope

import javax.inject.Scope

/**
 * Identifies a scope which lasts as a screen's life.
 */
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ScreenScope