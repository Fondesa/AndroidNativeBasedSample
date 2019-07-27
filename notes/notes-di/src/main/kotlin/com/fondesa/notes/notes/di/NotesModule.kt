package com.fondesa.notes.notes.di

import com.fondesa.notes.core.api.AppInitializer
import com.fondesa.notes.notes.api.NotesRepository
import com.fondesa.notes.notes.impl.NativeNotesRepository
import com.fondesa.notes.notes.impl.NotesDatabaseInitializer
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet

@Module
interface NotesModule {

    @Binds
    fun provideNotesRepository(repository: NativeNotesRepository): NotesRepository

    @Binds
    @IntoSet
    fun provideNotesDatabaseInitializer(initializer: NotesDatabaseInitializer): AppInitializer
}