package com.fondesa.notes.thread.di

import com.fondesa.notes.thread.api.CoroutineContextProvider
import com.fondesa.notes.thread.impl.CoroutineContextProviderImpl
import dagger.Binds
import dagger.Module

@Module
interface ThreadModule {

    @Binds
    fun provideCoroutineContextProvider(provider: CoroutineContextProviderImpl): CoroutineContextProvider
}