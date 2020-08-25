package net.kibotu.coroutines

import android.app.Application
import timber.log.Timber

/**
 * Created by [Jan Rabe](https://http://kibotu.net).
 */

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}