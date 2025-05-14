package dev.vincenzocostagliola.dodo

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.HiltAndroidApp
import dev.vincenzocostagliola.dodo.LoggingSetup.setupLogging

@HiltAndroidApp
class DodoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this);
        setupLogging()
    }
}