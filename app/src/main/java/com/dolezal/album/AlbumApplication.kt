package com.dolezal.album

import android.app.Application
import com.facebook.stetho.Stetho
import toothpick.Toothpick

class AlbumApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Toothpick.openScope(applicationContext).apply {
            installModules(AlbumToothpickModule)
        }
        Stetho.initializeWithDefaults(this);
    }
}