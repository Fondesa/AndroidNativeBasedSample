package com.fondesa.notes.di

import android.content.Context
import com.fondesa.notes.App
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface AppModule {

    @Singleton
    @Binds
    fun provideContext(app: App): Context
}