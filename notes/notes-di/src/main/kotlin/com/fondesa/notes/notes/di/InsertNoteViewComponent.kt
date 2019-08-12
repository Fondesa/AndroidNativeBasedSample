package com.fondesa.notes.notes.di

import com.fondesa.notes.notes.impl.InsertNoteView
import com.fondesa.notes.ui.api.injection.ViewInjector
import dagger.Subcomponent

@Subcomponent
interface InsertNoteViewComponent : ViewInjector<InsertNoteView> {

    @Subcomponent.Builder
    interface Builder : ViewInjector.Builder<InsertNoteView>
}