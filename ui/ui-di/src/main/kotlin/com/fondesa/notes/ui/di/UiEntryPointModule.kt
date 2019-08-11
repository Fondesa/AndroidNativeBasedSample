package com.fondesa.notes.ui.di

import androidx.lifecycle.LifecycleOwner
import com.fondesa.notes.ui.api.view.DelayedTextWatcher
import com.fondesa.notes.ui.api.view.TextWatcherFactory
import com.fondesa.notes.ui.impl.view.DelayedTextChangeWatcherThreshold
import com.fondesa.notes.ui.impl.view.DelayedTextWatcherFactory
import dagger.Binds
import dagger.Module
import dagger.Provides

/**
 * To satisfy the dependency graph, an instance of [LifecycleOwner] should be provided.
 */
@Module(includes = [UiEntryPointModule.WithProvides::class])
interface UiEntryPointModule {

    @DelayedTextWatcher
    @Binds
    fun provideDelayedTextWatcherFactory(factory: DelayedTextWatcherFactory): TextWatcherFactory

    @Module
    object WithProvides {

        @DelayedTextChangeWatcherThreshold
        @Provides
        @JvmStatic
        fun provideDelayedTextChangeWatcherThresholdMs(): Long = 500
    }
}