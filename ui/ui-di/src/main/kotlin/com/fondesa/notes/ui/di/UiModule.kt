package com.fondesa.notes.ui.di

import com.fondesa.notes.ui.api.view.ImmediateTextWatcher
import com.fondesa.notes.ui.api.view.TextWatcherFactory
import com.fondesa.notes.ui.impl.view.ImmediateTextWatcherFactory
import dagger.Binds
import dagger.Module

@Module
interface UiModule {

    @ImmediateTextWatcher
    @Binds
    fun provideImmediateTextWatcherFactory(factory: ImmediateTextWatcherFactory): TextWatcherFactory
}