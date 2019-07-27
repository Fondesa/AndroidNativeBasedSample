package com.fondesa.notes.thread.api

import kotlin.coroutines.CoroutineContext

/**
 * Provides the [CoroutineContext]s used to execute the coroutines' operations.
 * This interface is necessary to inject different test's contexts.
 */
@Suppress("PropertyName")
interface CoroutineContextProvider {

    /**
     * Provides the [CoroutineContext] for operations on the main thread.
     */
    val Main: CoroutineContext

    /**
     * Provides the [CoroutineContext] for IO operations.
     */
    val IO: CoroutineContext
}