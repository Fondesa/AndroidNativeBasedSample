package com.fondesa.notes.notes.di

import com.fondesa.notes.core.api.AppInitializer
import com.fondesa.notes.notes.api.DraftNotesRepository
import com.fondesa.notes.notes.api.NotesRepository
import com.fondesa.notes.notes.impl.*
import com.fondesa.notes.ui.api.qualifiers.ScreenScope
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoSet

@Module
interface NotesModule {

    @Binds
    fun provideNotesRepository(repository: NativeNotesRepository): NotesRepository

    @Binds
    fun provideDraftNotesRepository(repository: NativeDraftNotesRepository): DraftNotesRepository

    @Binds
    @IntoSet
    fun provideNotesDatabaseInitializer(initializer: NotesDatabaseInitializer): AppInitializer

    @ScreenScope
    @ContributesAndroidInjector(modules = [ScreenBinds::class])
    fun notesListActivity(): NotesActivity

    @Module
    interface ScreenBinds {

        @Binds
        fun provideView(activity: NotesActivity): NotesContract.View

        @Binds
        fun providePresenter(presenter: NotesPresenter): NotesContract.Presenter

        @Binds
        fun provideHolderFactory(factory: NoteRecyclerViewHolderImplFactory): NoteRecyclerViewHolderFactory

        @Binds
        fun provideAdapter(adapter: NoteRecyclerViewAdapterImpl): NoteRecyclerViewAdapter

        @Binds
        fun provideOnNoteClickListener(activity: NotesActivity): NoteRecyclerViewAdapter.OnNoteClickListener
    }
}