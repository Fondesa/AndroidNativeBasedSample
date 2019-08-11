package com.fondesa.notes.notes.di

import androidx.lifecycle.LifecycleObserver
import com.fondesa.notes.core.api.AppInitializer
import com.fondesa.notes.notes.api.NotesInteractor
import com.fondesa.notes.notes.impl.*
import com.fondesa.notes.ui.api.scope.ScreenScope
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoSet

@Module
interface NotesModule {

    @Binds
    fun provideNotesInteractor(repository: NativeNotesInteractor): NotesInteractor

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
        @IntoSet
        fun providePresenterLifecycleObserver(presenter: NotesPresenter): LifecycleObserver

        @Binds
        fun provideHolderFactory(factory: NoteRecyclerViewHolderImplFactory): NoteRecyclerViewHolderFactory

        @Binds
        fun provideAdapter(adapter: NoteRecyclerViewAdapterImpl): NoteRecyclerViewAdapter

        @Binds
        fun provideOnNoteClickListener(activity: NotesActivity): NoteRecyclerViewAdapter.OnNoteClickListener
    }
}