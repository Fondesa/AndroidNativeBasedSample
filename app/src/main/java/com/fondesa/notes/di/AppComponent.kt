package com.fondesa.notes.di

import com.fondesa.notes.App
import com.fondesa.notes.log.di.LogModule
import com.fondesa.notes.notes.di.NotesModule
import com.fondesa.notes.thread.di.ThreadModule
import com.fondesa.notes.ui.di.UiModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        LogModule::class,
        NotesModule::class,
        ThreadModule::class,
        UiModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: App): Builder

        fun build(): AppComponent
    }
}