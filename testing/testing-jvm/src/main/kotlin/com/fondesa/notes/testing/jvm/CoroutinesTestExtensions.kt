package com.fondesa.notes.testing.jvm

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking

/**
 * Used to avoid the return type of [runBlocking] to unit test suspend functions.
 *
 * @param block the block which must be executed.
 */
fun runUnitBlocking(block: suspend CoroutineScope.() -> Unit) {
    runBlocking {
        block(this)
    }
}