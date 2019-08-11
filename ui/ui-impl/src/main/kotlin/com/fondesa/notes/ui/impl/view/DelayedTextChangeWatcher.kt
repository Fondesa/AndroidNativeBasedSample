/*
 * Copyright (c) 2019 Fondesa
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fondesa.notes.ui.impl.view

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.fondesa.notes.log.api.Log
import com.fondesa.notes.thread.api.CoroutineContextProvider
import com.fondesa.notes.thread.api.launchWithDelay
import com.fondesa.notes.ui.api.view.TextWatcherFactory
import com.google.auto.factory.AutoFactory
import com.google.auto.factory.Provided
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

@AutoFactory(implementing = [TextWatcherFactory::class])
class DelayedTextChangeWatcher(
    @Provided private val contextProvider: CoroutineContextProvider,
    @Provided private val lifecycleOwner: LifecycleOwner,
    @[Provided DelayedTextChangeWatcherThreshold] private val thresholdMs: Long,
    private inline val onChange: (String) -> Unit
) : TextWatcher,
    CoroutineScope,
    LifecycleObserver {

    override val coroutineContext: CoroutineContext
        get() = job + contextProvider.IO

    private val job = Job()

    private var currentText: String = ""

    init {
        // Add this as a lifecycle observer when it's created.
        lifecycleOwner.lifecycle.addObserver(this)
    }

    override fun afterTextChanged(s: Editable?) {
        val text = s?.toString() ?: ""
        if (currentText == text) {
            return
        }

        currentText = text

        job.cancelChildren()

        launchWithDelay(thresholdMs) {
            Log.d("Search text changed: $text")
            withContext(contextProvider.Main) {
                onChange(text)
            }
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        job.cancel()
    }
}