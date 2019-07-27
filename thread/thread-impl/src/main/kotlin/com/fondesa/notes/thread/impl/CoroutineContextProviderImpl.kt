package com.fondesa.notes.thread.impl

import com.fondesa.notes.thread.api.CoroutineContextProvider
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Implementation of [CoroutineContextProvider] which returns the related [Dispatchers].
 */
class CoroutineContextProviderImpl @Inject constructor() : CoroutineContextProvider {
    override val Main: CoroutineContext = Dispatchers.Main
    override val IO: CoroutineContext = Dispatchers.IO
}