package com.fondesa.notes.notes.di

import com.fondesa.notes.notes.impl.SearchView
import com.fondesa.notes.ui.api.injection.ViewInjector
import dagger.Subcomponent

@Subcomponent(modules = [SearchViewModule::class])
interface SearchViewComponent : ViewInjector<SearchView> {

    @Subcomponent.Builder
    interface Builder : ViewInjector.Builder<SearchView>
}