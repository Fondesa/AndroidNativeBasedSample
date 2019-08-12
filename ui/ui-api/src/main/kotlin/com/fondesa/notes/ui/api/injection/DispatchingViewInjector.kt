package com.fondesa.notes.ui.api.injection

import javax.inject.Inject
import javax.inject.Provider

class DispatchingViewInjector @Inject constructor(
    private val builders: MutableMap<Class<*>, Provider<ViewInjector.Builder<*>>>
) : ViewInjector<Any> {

    override fun inject(target: Any) {
        getInjectorBuilder(target::class.java)
            ?.target(target)
            ?.build()
            ?.inject(target)
    }

    @Suppress("UNCHECKED_CAST")
    private fun getInjectorBuilder(clazz: Class<*>): ViewInjector.Builder<Any>? =
        builders[clazz]?.get() as ViewInjector.Builder<Any>?
}