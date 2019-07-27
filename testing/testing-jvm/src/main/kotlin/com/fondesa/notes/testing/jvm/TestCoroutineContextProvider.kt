package com.fondesa.notes.testing.jvm

import com.fondesa.notes.thread.api.CoroutineContextProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.coroutines.CoroutineContext

/**
 * Implementation of [CoroutineContextProvider] for testing purposes which uses the
 * unconfined dispatchers for both contexts.
 */
class TestCoroutineContextProvider : CoroutineContextProvider {
    @ExperimentalCoroutinesApi
    override val Main: CoroutineContext = Dispatchers.Unconfined
    @ExperimentalCoroutinesApi
    override val IO: CoroutineContext = Dispatchers.Unconfined
}