package com.fondesa.notes.testing.android

import android.app.Activity
import androidx.test.rule.ActivityTestRule

/**
 * Creates a new [ActivityTestRule] for given [Activity] type.
 *
 * @param launchActivity true if the [Activity] should be launched automatically.
 * @param T the type of the [Activity].
 * @return a new [ActivityTestRule].
 */
inline fun <reified T : Activity> activityTestRuleOf(launchActivity: Boolean = true): ActivityTestRule<T> =
    ActivityTestRule(T::class.java, false, launchActivity)

/**
 * Executes [block] on the UI thread.
 *
 * @param block the block which should be executed.
 * @param T the type of the [Activity].
 */
inline fun <T : Activity> ActivityTestRule<T>.onActivity(crossinline block: T.() -> Unit) {
    runOnUiThread {
        block(activity)
    }
}