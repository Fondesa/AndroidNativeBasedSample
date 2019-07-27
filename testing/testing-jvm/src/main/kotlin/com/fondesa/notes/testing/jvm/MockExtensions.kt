package com.fondesa.notes.testing.jvm

import org.mockito.stubbing.OngoingStubbing

/**
 * Throws an unchecked [Throwable].
 * This method is useful to avoid the pollution of [Throws] annotation in Kotlin code only
 * for testing purposes.
 *
 * @param t the [Throwable] which should be thrown.
 * @return the stubbing itself.
 */
fun <T> OngoingStubbing<T>.thenThrowUnchecked(t: Throwable): OngoingStubbing<T> = thenAnswer {
    throw t
}