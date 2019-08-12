package com.fondesa.notes.ui.api.injection

import dagger.BindsInstance

interface ViewInjector<T> {

    fun inject(target: T)

    interface Builder<T> {

        @BindsInstance
        fun target(target: T): Builder<T>

        fun build(): ViewInjector<T>
    }
}