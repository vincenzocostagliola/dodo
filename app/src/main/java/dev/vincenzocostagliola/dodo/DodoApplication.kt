package dev.vincenzocostagliola.dodo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import dev.vincenzocostagliola.dodo.LoggingSetup.setupLogging

@HiltAndroidApp
class DodoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setupLogging()
    }
}