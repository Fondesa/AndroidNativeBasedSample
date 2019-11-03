package com.fondesa.notes.ui.di

import com.fondesa.notes.ui.api.time.UiDateFormatter
import com.fondesa.notes.ui.api.view.ImmediateTextWatcher
import com.fondesa.notes.ui.api.view.TextWatcherFactory
import com.fondesa.notes.ui.impl.view.ImmediateTextChangeWatcherFactory
import com.fondesa.notes.ui.impl.view.UiDateFormatterImpl
import dagger.Binds
import dagger.Module

@Module
interface UiModule {

    @ImmediateTextWatcher
    @Binds
    fun provideImmediateTextWatcherFactory(factory: ImmediateTextChangeWatcherFactory): TextWatcherFactory

    @Binds
    fun provideUiDateFormatter(formatter: UiDateFormatterImpl): UiDateFormatter
}