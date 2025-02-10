package com.ldileh.githubuser

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.ENABLE_LOGGING){
            Timber.plant(Timber.DebugTree())
        }
    }
}

