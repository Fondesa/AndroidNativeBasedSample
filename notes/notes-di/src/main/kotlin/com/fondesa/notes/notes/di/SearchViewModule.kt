package com.fondesa.notes.notes.di

import androidx.lifecycle.LifecycleOwner
import com.fondesa.notes.notes.impl.SearchView
import com.fondesa.notes.ui.di.UiEntryPointModule
import dagger.Binds
import dagger.Module

@Module(includes = [UiEntryPointModule::class])
interface SearchViewModule {

    @Binds
    fun provideLifecycleOwner(owner: SearchView): LifecycleOwner
}