package com.fondesa.notes

import android.content.Context
import androidx.multidex.MultiDex
import com.fondesa.notes.core.api.AppInitializer
import com.fondesa.notes.di.DaggerAppComponent
import com.fondesa.notes.log.api.Log
import com.fondesa.notes.log.api.Logger
import com.squareup.leakcanary.LeakCanary
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import javax.inject.Inject

/**
 * Main entry point of the entire application.
 */
class App : DaggerApplication() {

    @Inject
    lateinit var logger: Logger

    @Inject
    lateinit var appInitializers: Set<@JvmSuppressWildcards AppInitializer>

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        // Needed because the app exceeds 65K methods.
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // In this case the app mustn't be initialized.
            return
        }
        // Install LeakCanary to enable the leak monitoring.
        LeakCanary.install(this)

        Log.initialize(logger)

        appInitializers.forEach {
            it.initialize()
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.builder()
            .application(this)
            .build().also {
                it.inject(this)
            }


    companion object {
        init {
            // This is necessary below api 17 because all the native library's dependencies should
            // be loaded manually.
            System.loadLibrary("nativemobile")
            System.loadLibrary("notes-native")
        }
    }
}